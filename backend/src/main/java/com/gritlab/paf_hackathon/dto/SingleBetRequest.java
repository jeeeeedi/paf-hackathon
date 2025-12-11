package com.gritlab.paf_hackathon.dto;

import com.gritlab.paf_hackathon.model.Outcome;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SingleBetRequest {

    @NotBlank(message = "playerName required")
    private String playerName;

    @NotBlank(message = "matchId required")
    private String matchId;

    @NotBlank(message = "outcome required")
    private Outcome outcome;

    @NotNull(message = "Stake required")
    @DecimalMin(value = "0.01", message = "Minimum: 0.01")
    private Number stake;

    // Default constructor
    public SingleBetRequest() {}

    public SingleBetRequest(String playerName, String matchId, Outcome outcome, Number stake) {
        this.playerName = playerName;
        this.matchId = matchId;
        this.outcome = outcome;
        this.stake = stake;
    }

    // Getters and Setters
    public String gePlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

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

    public Number getStake() {
        return stake;
    }

    public void setStake(Number stake) {
        this.stake = stake;
    }
}
