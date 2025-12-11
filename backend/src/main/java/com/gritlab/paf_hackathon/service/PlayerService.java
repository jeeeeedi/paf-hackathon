package com.gritlab.paf_hackathon.service;

import com.gritlab.paf_hackathon.model.Player;
import com.gritlab.paf_hackathon.repository.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayersRepository playersRepository;

    public Player createPlayer(Player player) {
        // Ensure the player name is unique
        if (playersRepository.existsByName(player.getName())) {
            throw new IllegalArgumentException("Player name must be unique");
        }
        return playersRepository.save(player);
    }
}