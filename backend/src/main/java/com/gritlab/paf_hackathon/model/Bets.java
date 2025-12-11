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
    private Double stake;

    @NotNull
    @Field("FinalOdds")
    private Double finalOdds;

    @NotNull
    @Field("PotentialPayout")
    private Double potentialPayout;

    @Field("WinAmount")
    private Double winAmount;

    @NotNull
    @Field("PlacedAt")
    private OffsetDateTime placedAt;

    @Field("SettledAt")
    private OffsetDateTime settledAt;

    @NotNull
    @Field("Selections")
    private List<BetSelection> selections;
}
