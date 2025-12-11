package com.gritlab.paf_hackathon.dto;

import com.gritlab.paf_hackathon.model.Outcome;

import jakarta.validation.constraints.NotBlank;

public class BetSelectionRequest {
    @NotBlank(message = "matchId required")
    private String matchId;

    @NotBlank(message = "outcome required")
    private Outcome outcome;

    // Default constructor
    public BetSelectionRequest() {}

    public BetSelectionRequest(String matchId, Outcome outcome) {
        this.matchId = matchId;
        this.outcome = outcome;
    }

    // Getters and Setters

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Outcome getOutcome() {
        return outcome;
    }

    public void setOutcome(Outcome outcome) {
        this.outcome = outcome;
    }
}
