package com.gritlab.paf_hackathon.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CombinationBetRequest {

    @NotBlank(message = "playerName required")
    private String playerName;

    @NotBlank(message = "selections required")
    @Size(min = 2, message = "At least 2 selections are required")
    private BetSelectionRequest selections;

    @NotNull(message = "Stake required")
    @DecimalMin(value = "0.01", message = "Minimum: 0.01")
    private Number stake;

    // Default constructor
    public CombinationBetRequest() {}

    public CombinationBetRequest(String playerName, BetSelectionRequest selections, Number stake) {
        this.playerName = playerName;
        this.selections = selections;
        this.stake = stake;
    }

    // Getters and Setters
    public String gePlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public BetSelectionRequest getSelections() {
        return selections;
    }

    public void setOutcome(BetSelectionRequest selections) {
        this.selections = selections;
    }

    public Number getStake() {
        return stake;
    }

    public void setStake(Number stake) {
        this.stake = stake;
    }
}
