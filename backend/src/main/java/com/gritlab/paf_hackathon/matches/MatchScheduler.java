package com.gritlab.paf_hackathon.matches;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Random;

@Component
public class MatchScheduler {

    private static final Logger log = LoggerFactory.getLogger(MatchScheduler.class);
    private final MatchRepository repo;
    private final KafkaTemplate<String, MatchFinishedEvent> kafkaTemplate;
    private final Random random = new Random();

    @Value("${match.finished.topic:match-finished-events}")
    private String matchFinishedTopic;

    public MatchScheduler(MatchRepository repo,
            KafkaTemplate<String, MatchFinishedEvent> kafkaTemplate) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10_000) // every 10 seconds
    public void updateMatchStatuses() {
        OffsetDateTime now = OffsetDateTime.now();

        repo.findAll().forEach(match -> {
            if (match.getStatus() == MatchStatus.UPCOMING
                    && now.isAfter(match.getKickoffAt())
                    && now.isBefore(match.getEndsAt())) {
                match.setStatus(MatchStatus.IN_PROGRESS);
                repo.save(match);
                log.info("Match {} is now IN_PROGRESS", match.getId());
            } else if ((match.getStatus() == MatchStatus.UPCOMING
                    || match.getStatus() == MatchStatus.IN_PROGRESS)
                    && now.isAfter(match.getEndsAt())) {

                if (match.getResult() == null) {
                    match.setResult(randomOutcome());
                }
                match.setStatus(MatchStatus.FINISHED);
                repo.save(match);
                log.info("Match {} finished with result {}", match.getId(), match.getResult());

                // Evict cache so /matches reflects the change immediately
                evictOpenMatchesCache();

                // Publish MatchFinished event to Kafka
                publishMatchFinishedEvent(match);
            }
        });
    }

    private void publishMatchFinishedEvent(Match match) {
        MatchFinishedEvent event = new MatchFinishedEvent(
                match.getId(),
                match.getHomeTeam(),
                match.getAwayTeam(),
                match.getResult(),
                match.getEndsAt().toString());

        kafkaTemplate.send(matchFinishedTopic, match.getId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Published MatchFinishedEvent to Kafka: {}", event);
                    } else {
                        log.error("Failed to publish MatchFinishedEvent for match {}: {}",
                                match.getId(), ex.getMessage());
                    }
                });
    }

    @CacheEvict(value = "userMatchesCache", allEntries = true)
    public void evictOpenMatchesCache() {
        log.debug("Cache 'userMatchesCache' evicted after match finished");
    }

    private Outcome randomOutcome() {
        int r = random.nextInt(3);
        return switch (r) {
            case 0 -> Outcome.HOME;
            case 1 -> Outcome.DRAW;
            default -> Outcome.AWAY;
        };
    }
}
