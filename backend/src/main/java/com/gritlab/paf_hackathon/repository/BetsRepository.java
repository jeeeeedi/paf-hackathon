package com.gritlab.paf_hackathon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gritlab.paf_hackathon.model.Bets;

public interface BetsRepository extends MongoRepository<Bets, String> {
    
}
