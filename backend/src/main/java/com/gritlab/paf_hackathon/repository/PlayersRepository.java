package com.gritlab.paf_hackathon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gritlab.paf_hackathon.model.Player;

public interface PlayersRepository extends MongoRepository<Player, String> {
    
}
