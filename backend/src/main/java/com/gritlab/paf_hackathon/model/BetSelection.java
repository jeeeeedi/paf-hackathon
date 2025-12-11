package com.gritlab.paf_hackathon.model;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import jakarta.validation.constraints.NotNull;

@Document(collection = "betSelection")
public class BetSelection {
    
    @NotNull
    @Field("matchId")
    private String matchId;

    @NotNull
    @Field("Outcome")
    private Outcome outcome;

    @NotNull
    @Field("Odds")
    private Double oods;
}
