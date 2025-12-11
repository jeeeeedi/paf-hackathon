package com.gritlab.paf_hackathon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gritlab.paf_hackathon.model.Match;

public interface MatchesRepository extends MongoRepository<Match, String> {
    
}
