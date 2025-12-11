package com.gritlab.paf_hackathon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
// import jakarta.validation.constraints.NotNull;
// import jakarta.validation.constraints.Size;
// import jakarta.validation.constraints.Email;

@Document(collection = "players")
public class Player {

    @Id
    private String id; // ($uuid)

    // @NotNull
    @Field("name")
    private String name;

    // @NotNull
    @Field("balance")
    private double balance; // ($double)

    // @NotNull
    @Field("createdAt")
    private String createdAt; // ($date-time)

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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}