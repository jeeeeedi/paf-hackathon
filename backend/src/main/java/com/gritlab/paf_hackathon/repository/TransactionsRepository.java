package com.gritlab.paf_hackathon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gritlab.paf_hackathon.model.Transaction;

import java.util.List;

public interface TransactionsRepository extends MongoRepository<Transaction, String> {
    
    List<Transaction> findByPlayerName(String playerName);
}
