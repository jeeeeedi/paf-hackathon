package com.gritlab.paf_hackathon.matches;

// MatchController.java
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:5173") // or your frontend port
public class MatchController {

    private final MatchRepository repo;

    public MatchController(MatchRepository repo) {
        this.repo = repo;
    }

    // GET /matches – upcoming + in-progress
    @GetMapping("/matches")
    @Cacheable(value = "openMatches", unless = "#result == null || #result.body.isEmpty()")
    public ResponseEntity<List<Match>> listOpenMatches() {
        return ResponseEntity.ok(repo.findOpenOrInProgress());
    }

    // GET /matches/finished – finished only
    @GetMapping("/matches/finished")
    public ResponseEntity<List<Match>> listFinishedMatches() {
        return ResponseEntity.ok(repo.findFinished());
    }

    // Extra admin endpoints to drive the simulator during hackathon

    @PostMapping("/admin/matches/{id}/start")
    public ResponseEntity<Object> startMatch(@PathVariable UUID id) {
        return repo.findById(id)
                .map(m -> {
                    m.setStatus(MatchStatus.IN_PROGRESS);
                    repo.save(m);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/matches/{id}/finish")
    public ResponseEntity<Object> finishMatch(@PathVariable UUID id,
            @RequestParam Outcome result) {
        return repo.findById(id)
                .map(m -> {
                    m.setStatus(MatchStatus.FINISHED);
                    m.setResult(result);
                    repo.save(m);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
