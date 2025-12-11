package com.gritlab.paf_hackathon.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.gritlab.paf_hackathon.model.Player;

import org.springframework.stereotype.Repository;
import java.util.Optional;

public interface PlayersRepository extends MongoRepository<Player, String> {
    Optional<Player> findByNameIgnoreCase(String name);
    boolean existsByName(String name);
}
