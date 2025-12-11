package com.gritlab.paf_hackathon.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gritlab.paf_hackathon.model.MatchStatus;
import com.gritlab.paf_hackathon.model.Outcome;
import com.gritlab.paf_hackathon.repository.MatchRepository;

import java.time.OffsetDateTime;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MatchScheduler {

    private static final Logger log = LoggerFactory.getLogger(MatchScheduler.class);
    private final MatchRepository repo;
    private final KafkaTemplate<String, MatchFinishedEvent> kafkaTemplate;
    private final Random random = new Random();

    // ⬇️ NEW: Track which matches have been processed
    private final Set<UUID> processedMatches = ConcurrentHashMap.newKeySet();

    @Value("${match.finished.topic:match-finished-events}")
    private String matchFinishedTopic;

    public MatchScheduler(MatchRepository repo,
            KafkaTemplate<String, MatchFinishedEvent> kafkaTemplate) {
        this.repo = repo;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 10_000) // Run every 10 seconds
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

                // ⬇️ IDEMPOTENCY CHECK: Only process if not already processed
                if (processedMatches.contains(match.getId())) {
                    log.debug("Match {} already processed, skipping", match.getId());
                    return;
                }

                // Mark as processed IMMEDIATELY to prevent duplicate processing
                processedMatches.add(match.getId());

                try {
                    if (match.getResult() == null) {
                        match.setResult(randomOutcome());
                    }
                    match.setStatus(MatchStatus.FINISHED);
                    repo.save(match);
                    log.info("Match {} finished with result {}", match.getId(), match.getResult());

                    // Evict cache
                    evictOpenMatchesCache();

                    // Publish event
                    publishMatchFinishedEvent(match);

                } catch (Exception ex) {
                    log.error("Error processing match finish for {}: {}",
                            match.getId(), ex.getMessage());
                    // Remove from processed set so it can be retried
                    processedMatches.remove(match.getId());
                }
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
                        log.info("Published MatchFinishedEvent to Kafka (idempotent): {}", event);
                    } else {
                        log.error("Failed to publish MatchFinishedEvent for match {}: {}",
                                match.getId(), ex.getMessage());
                    }
                });
    }

    @CacheEvict(value = "openMatches", allEntries = true)
    public void evictOpenMatchesCache() {
        log.debug("Cache 'openMatches' evicted after match finished");
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
