package com.gritlab.paf_hackathon.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gritlab.paf_hackathon.model.Match;
import com.gritlab.paf_hackathon.model.MatchStatus;
import com.gritlab.paf_hackathon.dto.SeedMatchDto;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MatchRepository {

    private final Map<UUID, Match> matches = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        loadFromSeedFile(objectMapper);
    }

    private void loadFromSeedFile(ObjectMapper objectMapper) {
        matches.clear();
        try {
            ClassPathResource resource = new ClassPathResource("football_matches.json");
            try (InputStream is = resource.getInputStream()) {
                List<SeedMatchDto> seed = objectMapper.readValue(
                        is,
                        new TypeReference<List<SeedMatchDto>>() {
                        });
                OffsetDateTime base = OffsetDateTime.now();
                for (SeedMatchDto row : seed) {
                    OffsetDateTime kickoff = base.plusSeconds(row.getStart_time_delay());
                    OffsetDateTime ends = kickoff.plusSeconds(row.getMatch_duration());

                    Match m = new Match();
                    m.setId(UUID.randomUUID());
                    m.setHomeTeam(row.getHome_team());
                    m.setAwayTeam(row.getAway_team());
                    m.setHomeOdds(row.getHome_team_odds());
                    m.setDrawOdds(row.getDraw_odds());
                    m.setAwayOdds(row.getAway_team_odds());
                    m.setKickoffAt(kickoff);
                    m.setEndsAt(ends);
                    m.setStatus(MatchStatus.UPCOMING);
                    m.setResult(null);

                    matches.put(m.getId(), m);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load football_matches.json", e);
        }
    }

    public List<Match> findOpenOrInProgress() {
        return matches.values().stream()
                .filter(m -> m.getStatus() == MatchStatus.UPCOMING
                        || m.getStatus() == MatchStatus.IN_PROGRESS)
                .sorted(Comparator.comparing(Match::getKickoffAt))
                .collect(Collectors.toList());
    }

    public List<Match> findFinished() {
        return matches.values().stream()
                .filter(m -> m.getStatus() == MatchStatus.FINISHED)
                .sorted(Comparator.comparing(Match::getEndsAt).reversed())
                .collect(Collectors.toList());
    }

    public Optional<Match> findById(UUID id) {
        return Optional.ofNullable(matches.get(id));
    }

    public Match save(Match m) {
        matches.put(m.getId(), m);
        return m;
    }

    public Collection<Match> findAll() {
        return matches.values();
    }
}
