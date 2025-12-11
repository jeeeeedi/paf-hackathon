package com.gritlab.paf_hackathon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gritlab.paf_hackathon.model.Bet;

public interface BetsRepository extends MongoRepository<Bet, String> {
    
}
