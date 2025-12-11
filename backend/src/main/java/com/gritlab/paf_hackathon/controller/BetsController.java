package com.gritlab.paf_hackathon.controller;

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

        // Convert match ID from String to UUID
        UUID matchId;
        try {
            matchId = UUID.fromString(request.getMatchId());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Invalid match ID
        }

        // Fetch match
        Optional<Match> matchOpt = matchRepository.findById(matchId);
        if (matchOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Match not found
        }
        Match match = matchOpt.get();

        // Check if betting is still open
        if (OffsetDateTime.now().isAfter(match.getKickoffAt()) || match.getStatus() != MatchStatus.UPCOMING) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Betting closed
        }
        
        // Fetch player
        Optional<Player> playerOpt = playersRepository.findById(request.gePlayerName());
        if (playerOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Player not found
        }
        Player player = playerOpt.get();

        double stake = request.getStake().doubleValue();
        double playerBalance = player.getBalance().doubleValue();

        // Check player balance
        if (playerBalance < stake) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Insufficient balance
        }
        
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
        
        // Create and save bet using BetsRepository
        BetSelection selection = new BetSelection();
        selection.setMatchId(match.getId().toString());
        selection.setOutcome(request.getOutcome());
        selection.setOdds(finalOdds);
        
        Bets bet = new Bets();
        bet.setPlayerName(player.getName());
        bet.setStake(stake);
        bet.setFinalOdds(finalOdds);
        bet.setPotentialPayout(potentialPayout);
        bet.setType(BetType.SINGLE);
        bet.setStatus(BetStatus.PLACED);
        bet.setPlacedAt(OffsetDateTime.now());
        bet.setSelections(Collections.singletonList(selection));
        
        betsRepository.save(bet); // Save bet to MongoDB

        // 8️⃣ Deduct stake from player balance and save player
        player.setBalance(playerBalance - stake);
        playersRepository.save(player);

        // 9️⃣ Return created bet
        return ResponseEntity.status(HttpStatus.CREATED).body(bet);
    }
    //bets cpmbinations
}
