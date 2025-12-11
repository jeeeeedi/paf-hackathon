package com.gritlab.paf_hackathon.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.NotNull;

@Document(collection = "betSelection")
public class BetSelection {
    @Override
    public String toString() {
        return "BetSelection{" +
                "matchId='" + matchId + '\'' +
                ", outcome=" + outcome +
                ", odds=" + odds +
                '}';
    }
    
    @NotNull
    @Field("matchId")
    private String matchId;

    @NotNull
    @Field("outcome")
    private Outcome outcome;

    @NotNull
    @Field("odds")
    private Number odds;

    public BetSelection() {
    }

    public BetSelection(String matchId, Outcome outcome, Number odds) {
        this.matchId = matchId;
        this.outcome = outcome;
        this.odds = odds;
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

    public Number getOdds() {
        return odds;
    }

    public void setOdds(Number odds) {
        this.odds = odds;
    }
}
