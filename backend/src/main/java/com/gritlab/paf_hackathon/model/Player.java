package com.gritlab.paf_hackathon.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;

@Document(collection = "players")
public class Player {

    @Id
    private String id; // ($uuid)

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("balance")
    private Number balance; // ($double)

    @CreatedDate
    @NotNull
    @Field("createdAt")
    private String createdAt; // ($date-time)

    public Player() {
    }

    public Player(String name, Number balance) {
        this.name = name;
        this.balance = balance;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getBalance() {
        return balance;
    }

    public void setBalance(Number balance) {
        this.balance = balance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}