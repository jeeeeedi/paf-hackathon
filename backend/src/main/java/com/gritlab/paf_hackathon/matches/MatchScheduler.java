package com.gritlab.paf_hackathon.matches;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Random;

@Component
public class MatchScheduler {

    private static final Logger log = LoggerFactory.getLogger(MatchScheduler.class);
    private final MatchRepository repo;
    private final Random random = new Random();

    public MatchScheduler(MatchRepository repo) {
        this.repo = repo;
    }

    @Scheduled(fixedRate = 1_000)
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

                // TODO: i need to publishMatchFinished Kafka event here
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
