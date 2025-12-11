package com.gritlab.paf_hackathon.service;

import com.gritlab.paf_hackathon.model.Player;
import com.gritlab.paf_hackathon.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player createPlayer(Player player) {
        // Ensure the player name is unique
        if (playerRepository.existsByName(player.getName())) {
            throw new IllegalArgumentException("Player name must be unique");
        }
        return playerRepository.save(player);
    }
}