package com.gritlab.paf_hackathon.controller;

import com.gritlab.paf_hackathon.model.Player;
import com.gritlab.paf_hackathon.model.Bets;
import com.gritlab.paf_hackathon.model.Transaction;
import com.gritlab.paf_hackathon.repository.PlayersRepository;
import com.gritlab.paf_hackathon.repository.BetsRepository;
import com.gritlab.paf_hackathon.repository.TransactionsRepository;
import com.gritlab.paf_hackathon.dto.PlayerRequest;
import com.gritlab.paf_hackathon.dto.TransactionResponse;
import com.gritlab.paf_hackathon.exception.PlayerAlreadyExistsException;
import com.gritlab.paf_hackathon.exception.PlayerNotFoundException;
import com.gritlab.paf_hackathon.exception.GlobalExceptionHandler;

import java.util.List;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/players")
@Validated
public class PlayerController {

    @Autowired
    private PlayersRepository playersRepository;
    @Autowired
    private BetsRepository betsRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;

    @GetMapping("/{playerName}")
    public ResponseEntity<Player> getPlayerByName(@PathVariable String playerName) {
        // Check if player exists
        if (!playersRepository.existsByName(playerName)) {
            throw new PlayerNotFoundException("Player not found");
        }

        Optional<Player> playerOpt = playersRepository.findByNameIgnoreCase(playerName);
        if (playerOpt.isPresent()) {
            return new ResponseEntity<>(playerOpt.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{playerName}/bets")
    public ResponseEntity<List<Bets>> getPlayerBets(@PathVariable String playerName) {
        // Check if player exists
        if (!playersRepository.existsByName(playerName)) {
            throw new PlayerNotFoundException("Player not found");
        }

        try {
            List<Bets> playerBets = betsRepository.findByPlayerName(playerName);
            return ResponseEntity.ok(playerBets);
        } catch (Exception e) {
            System.out.println("Error retrieving bets for player: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        }
    }

    @GetMapping("/{playerName}/transactions")
    public ResponseEntity<List<TransactionResponse>> getPlayerTransactions(@PathVariable String playerName) {
        // Check if player exists
        if (!playersRepository.existsByName(playerName)) {
            throw new PlayerNotFoundException("Player not found");
        }

        try {
            List<Transaction> playerTransactions = transactionsRepository.findByPlayerName(playerName);
            List<TransactionResponse> response = playerTransactions.stream()
                    .map(this::mapToTransactionResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error retrieving transactions for player: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerRequest playerRequest) {
        if (playersRepository.existsByName(playerRequest.name())) {
            throw new PlayerAlreadyExistsException("Player with this name already exists");
        }
        Player savedPlayer = playersRepository.save(new Player(playerRequest.name(), playerRequest.initialBalance()));
        // System.out.println("Created new player: " + savedPlayer.getInitialBalance());
        return new ResponseEntity<>(savedPlayer, HttpStatus.CREATED);
    }

    private TransactionResponse mapToTransactionResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getPlayerName(),
                transaction.getType(),
                transaction.getReason(),
                transaction.getAmount(),
                transaction.getBalanceAfter(),
                transaction.getReferenceBetId(),
                transaction.getCreatedAt());
    }
}