package com.gritlab.paf_hackathon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Document(collection = "transactions")
public class Transaction {
    @Id
    private String id;

    @NotNull
    @Field("PlayerName")
    private String playerName;

    @NotNull
    @Field("Type")
    private TransactionType type;

    @NotNull
    @Field("Reason")
    private String reason;

    @NotNull
    @Field("Amount")
    private Double amount;

    @NotNull
    @Field("BalanceAfter")
    private Double balanceAfter;

    @Field("ReferenceBetId")
    private String referenceBetId;

    @NotNull
    @Field("CreatedAt")
    private LocalDateTime createdAt;

    // Constructors
    public Transaction() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
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

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalanceAfter() {
        return balanceAfter;
    }

    public void setBalanceAfter(Double balanceAfter) {
        this.balanceAfter = balanceAfter;
    }

    public String getReferenceBetId() {
        return referenceBetId;
    }

    public void setReferenceBetId(String referenceBetId) {
        this.referenceBetId = referenceBetId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public enum TransactionType {
        DEBIT, CREDIT
    }
}