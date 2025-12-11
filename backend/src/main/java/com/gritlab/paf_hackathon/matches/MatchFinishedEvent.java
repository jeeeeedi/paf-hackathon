package com.gritlab.paf_hackathon.matches;

import java.util.UUID;

public class MatchFinishedEvent {

    private UUID matchId;
    private String homeTeam;
    private String awayTeam;
    private Outcome result;
    private String finishedAt;

    public MatchFinishedEvent() {
    }

    public MatchFinishedEvent(UUID matchId, String homeTeam, String awayTeam,
            Outcome result, String finishedAt) {
        this.matchId = matchId;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.result = result;
        this.finishedAt = finishedAt;
    }

    // Getters and setters
    public UUID getMatchId() {
        return matchId;
    }

    public void setMatchId(UUID matchId) {
        this.matchId = matchId;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public Outcome getResult() {
        return result;
    }

    public void setResult(Outcome result) {
        this.result = result;
    }

    public String getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(String finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public String toString() {
        return "MatchFinishedEvent{" +
                "matchId=" + matchId +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", result=" + result +
                ", finishedAt='" + finishedAt + '\'' +
                '}';
    }
}
