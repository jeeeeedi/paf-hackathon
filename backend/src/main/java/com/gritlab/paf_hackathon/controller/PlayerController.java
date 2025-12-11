package com.gritlab.paf_hackathon.controller;
import com.gritlab.paf_hackathon.model.Player;
import com.gritlab.paf_hackathon.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;
    
    @GetMapping("/{/{playerName}")
    public ResponseEntity<Player> getPlayerByName(@PathVariable String playerName) {
        Optional<Player> playerOpt = playerRepository.findByNameIgnoreCase(playerName);
        if (playerOpt.isPresent()) {
            return new ResponseEntity<>(playerOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{playerName}/bets")
    public ResponseEntity<List<Bet>> getPlayerBets(@PathVariable String playerName) {
        // Implementation to retrieve bets for the player
    }

    @GetMapping("{playerName}/transactions")
    public ResponseEntity<List<Transaction>> getPlayerTransactions(@PathVariable String playerName) {
        // Implementation to retrieve transactions for the player
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        if (!StringUtils.hasText(player.getName())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if (playerRepository.existsByName(player.getName())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        Player savedPlayer = playerRepository.save(player);
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }

}