package com.gritlab.paf_hackathon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bets")
public class Bets {
    @Id
    private String id;
    // Add fields as needed
}
