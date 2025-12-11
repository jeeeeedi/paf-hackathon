package com.gritlab.paf_hackathon.controller;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.gritlab.paf_hackathon.exception.GlobalExceptionHandler;
import com.gritlab.paf_hackathon.repository.BetsRepository;
import com.gritlab.paf_hackathon.repository.MatchRepository;
import com.gritlab.paf_hackathon.repository.PlayersRepository;
import com.gritlab.paf_hackathon.model.BetSelection;
import com.gritlab.paf_hackathon.model.BetStatus;
import com.gritlab.paf_hackathon.model.BetType;
import com.gritlab.paf_hackathon.model.Bets;
import com.gritlab.paf_hackathon.model.Match;
import com.gritlab.paf_hackathon.model.MatchStatus;
import com.gritlab.paf_hackathon.model.Player;
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

    System.out.println("Received SingleBetRequest: " + request);
        // Convert match ID from String to UUID
        UUID matchId;
        try {
            matchId = UUID.fromString(request.getMatchId());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid match ID: " + request.getMatchId());
        }

        System.out.println("matchId: " + matchId);

        // Fetch match
        Optional<Match> matchOpt = matchRepository.findById(matchId);
        if (matchOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Match not found
        }
        Match match = matchOpt.get();

        System.out.println("match found ");
        // Check if betting is still open
        if (OffsetDateTime.now().isAfter(match.getKickoffAt()) || match.getStatus() != MatchStatus.UPCOMING) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Betting closed
        }
        
        System.out.println("match open ");
        // Fetch player
        Optional<Player> playerOpt = playersRepository.findByName(request.gePlayerName());
        if (playerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Player not found
        }
        Player player = playerOpt.get();

        double stake = request.getStake().doubleValue();
        double playerBalance = player.getBalance().doubleValue();

        System.out.println("player: " + player);

        // Check player balance
        if (playerBalance < stake) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Insufficient balance
        }
        
        System.out.println("player can bet");

        // Calculate final odds based on outcome
        double finalOdds;
        switch (request.getOutcome()) {
            case HOME:
                finalOdds = match.getHomeOdds();
                break;
            case DRAW:
                finalOdds = match.getDrawOdds();
                break;
            case AWAY:
                finalOdds = match.getAwayOdds();
                break;
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Invalid outcome
        }
        double potentialPayout = stake * finalOdds;

        System.out.println("potentialPayout: " + potentialPayout);

        // Create and save bet using BetsRepository
        BetSelection selection = new BetSelection();
        selection.setMatchId(match.getId().toString());
        selection.setOutcome(request.getOutcome());
        selection.setOdds(finalOdds);

        System.out.println("created BetSelection");
        
        Bets bet = new Bets();
        bet.setPlayerName(player.getName());
        bet.setStake(stake);
        bet.setFinalOdds(finalOdds);
        bet.setPotentialPayout(potentialPayout);
        bet.setType(BetType.SINGLE);
        bet.setStatus(BetStatus.PLACED);
        bet.setPlacedAt(Instant.now());

        System.out.println("created Bets");

        bet.setSelections(Collections.singletonList(selection));
        
        System.out.println("set selection in bets");

        System.out.println("bet: "+ bet);
        try {
            betsRepository.save(bet); // Save bet to MongoDB

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("bet saved");

        // Deduct stake from player balance and save player
        player.setBalance(playerBalance - stake);
        playersRepository.save(player);
        System.out.println("update player");

        // Return created bet
        return ResponseEntity.status(HttpStatus.CREATED).body(bet);
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
