package com.gritlab.paf_hackathon.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
public class Matches {
    @Id
    private String id;
    // Add fields as needed
}
