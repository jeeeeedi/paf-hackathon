package com.gritlab.paf_hackathon.model;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Document(collection = "bets")
public class Bets {
    @Override
    public String toString() {
        return "Bets{" +
                "id='" + id + '\'' +
                ", playerName='" + playerName + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", stake=" + stake +
                ", finalOdds=" + finalOdds +
                ", potentialPayout=" + potentialPayout +
                ", winAmount=" + winAmount +
                ", placedAt=" + placedAt +
                ", settledAt=" + settledAt +
                ", selections=" + selections +
                '}';
    }

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
    private Instant placedAt;

    @Field("SettledAt")
    private Instant settledAt;

    @NotNull
    @Field("Selections")
    private List<BetSelection> selections;

    public Bets() {}

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

    public Instant getPlacedAt() {
        return placedAt;
    }

    public void setPlacedAt(Instant placedAt) {
        this.placedAt = placedAt;
    }

    public Instant getSettledAt() {
        return settledAt;
    }

    public void setSettledAt(Instant settledAt) {
        this.settledAt = settledAt;
    }

    public List<BetSelection> getSelections() {
        return selections;
    }

    public void setSelections(List<BetSelection> selections) {
        this.selections = selections;
    }

}
