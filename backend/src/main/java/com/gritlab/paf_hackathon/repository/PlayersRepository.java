package com.gritlab.paf_hackathon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gritlab.paf_hackathon.model.Players;

public interface PlayersRepository extends MongoRepository<Players, String> {
    
}
