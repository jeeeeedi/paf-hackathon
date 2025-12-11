package com.gritlab.paf_hackathon.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gritlab.paf_hackathon.model.Bets;

import java.util.List;

public interface BetsRepository extends MongoRepository<Bets, String> {

    // Find all bets by a specific player name
    List<Bets> findByPlayerNameIgnoreCase(String playerName);

    // Optionally, find bets with potential payout greater than a certain amount
    List<Bets> findByPotentialPayoutGreaterThan(double amount);
    
    List<Bets> findByPlayerName(String playerName);
}
