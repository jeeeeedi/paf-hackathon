package com.gritlab.paf_hackathon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gritlab.paf_hackathon.model.Bets;

import java.util.List;

public interface BetsRepository extends MongoRepository<Bets, String> {
    
    List<Bets> findByPlayerName(String playerName);
}
