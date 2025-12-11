package com.gritlab.paf_hackathon.matches;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Match {

    private UUID id;
    private String homeTeam;
    private String awayTeam;
    private double homeOdds;
    private double drawOdds;
    private double awayOdds;
    private OffsetDateTime kickoffAt;
    private OffsetDateTime endsAt;
    private MatchStatus status;
    private Outcome result; // nullable

    public Match() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public double getHomeOdds() {
        return homeOdds;
    }

    public void setHomeOdds(double homeOdds) {
        this.homeOdds = homeOdds;
    }

    public double getDrawOdds() {
        return drawOdds;
    }

    public void setDrawOdds(double drawOdds) {
        this.drawOdds = drawOdds;
    }

    public double getAwayOdds() {
        return awayOdds;
    }

    public void setAwayOdds(double awayOdds) {
        this.awayOdds = awayOdds;
    }

    public OffsetDateTime getKickoffAt() {
        return kickoffAt;
    }

    public void setKickoffAt(OffsetDateTime kickoffAt) {
        this.kickoffAt = kickoffAt;
    }

    public OffsetDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(OffsetDateTime endsAt) {
        this.endsAt = endsAt;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public Outcome getResult() {
        return result;
    }

    public void setResult(Outcome result) {
        this.result = result;
    }
}
