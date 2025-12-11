package com.gritlab.paf_hackathon.model;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Document(collection = "bets")
public class Bets {

    @Id
    private String id;

    @NotNull
    @Field("PlayerName")
    private String playerName;

    @NotNull
    @Field("Type")
    private BetType type;
    
    @NotNull
    @Field("Status")
    private BetStatus status;

    @NotNull
    @Positive
    @Field("Stake")
    private Number stake;

    @NotNull
    @Field("FinalOdds")
    private Number finalOdds;

    @NotNull
    @Field("PotentialPayout")
    private Number potentialPayout;

    @Field("WinAmount")
    private Number winAmount;

    @NotNull
    @Field("PlacedAt")
    private OffsetDateTime placedAt;

    @Field("SettledAt")
    private OffsetDateTime settledAt;

    @NotNull
    @Field("Selections")
    private List<BetSelection> selections;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public BetType getType() {
        return type;
    }

    public void setType(BetType type) {
        this.type = type;
    }

    public BetStatus getStatus() {
        return status;
    }

    public void setStatus(BetStatus status) {
        this.status = status;
    }

    public Number getStake() {
        return stake;
    }

    public void setStake(Number stake) {
        this.stake = stake;
    }

    public Number getFinalOdds() {
        return finalOdds;
    }

    public void setFinalOdds(Number finalOdds) {
        this.finalOdds = finalOdds;
    }

    public Number getPotentialPayout() {
        return potentialPayout;
    }

    public void setPotentialPayout(Number potentialPayout) {
        this.potentialPayout = potentialPayout;
    }

    public Number getWinAmount() {
        return winAmount;
    }

    public void setWinAmount(Number winAmount) {
        this.winAmount = winAmount;
    }

    public OffsetDateTime getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(OffsetDateTime placedAt) {
        this.placedAt = placedAt;
    }

    public OffsetDateTime getSettledAt() {
        return settledAt;
    }

    public void setSettledAt(OffsetDateTime settledAt) {
        this.settledAt = settledAt;
    }

    public List<BetSelection> getSelections() {
        return selections;
    }

    public void setSelections(List<BetSelection> selections) {
        this.selections = selections;
    }

}
