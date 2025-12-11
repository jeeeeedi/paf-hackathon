package com.gritlab.paf_hackathon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gritlab.paf_hackathon.repository.BetsRepository;
import com.gritlab.paf_hackathon.repository.MatchRepository;
import com.gritlab.paf_hackathon.repository.PlayersRepository;
import com.gritlab.paf_hackathon.model.Bets;
import com.gritlab.paf_hackathon.dto.SingleBetRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bets")
public class BetsController {

    @Autowired
    private BetsRepository betsRepository;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayersRepository playersRepository;

    //bets single
    @PostMapping("/single")
    public ResponseEntity<Bets> placeSingleBet(
            @Valid @RequestBody SingleBetRequest request
    ) {

        // TODO: 1. Validate that the match exists
        
        // TODO: 2. Validate that betting is still open
        // TODO: 3. Validate that user has sufficient balance
        // TODO: 4. Calculate odds/payout
        // TODO: 5. Save bet in DB using betsRepository

        // Example placeholder response until logic is implemented
        Bets placeholderBet = new Bets();
        placeholderBet.setPlayerName("temp");
        placeholderBet.setStake(request.getStake().doubleValue());
        placeholderBet.setFinalOdds(1.0);
        placeholderBet.setPotentialPayout(request.getStake().doubleValue());

        return ResponseEntity.status(HttpStatus.CREATED).body(placeholderBet);
    }

    @PostMapping("/combination")
    public ResponseEntity<Bets> placeCombinationBet(
            @Valid @RequestBody CombinationBetRequest request
    ) {
        // Similar TODOs as single bet but for combination bets

        // check if player has sufficient balance
        // calculate combined odds and potential payout
        // save bet in DB using betsRepository

        // Example placeholder response until logic is implemented
        Bets placeholderBet = new Bets();
        placeholderBet.setPlayerName("temp_combination");
        placeholderBet.setStake(request.getStake().doubleValue());
        placeholderBet.setFinalOdds(2.0);
        placeholderBet.setPotentialPayout(request.getStake().doubleValue() * 2);

        return ResponseEntity.status(HttpStatus.CREATED).body(placeholderBet);

    }
}
