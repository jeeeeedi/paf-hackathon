package com.gritlab.paf_hackathon.dto;

public class SeedMatchDto {
    private String home_team;
    private double home_team_odds;
    private double draw_odds;
    private String away_team;
    private double away_team_odds;
    private long start_time_delay; // seconds
    private long match_duration; // seconds

    public String getHome_team() {
        return home_team;
    }

    public void setHome_team(String home_team) {
        this.home_team = home_team;
    }

    public double getHome_team_odds() {
        return home_team_odds;
    }

    public void setHome_team_odds(double home_team_odds) {
        this.home_team_odds = home_team_odds;
    }

    public double getDraw_odds() {
        return draw_odds;
    }

    public void setDraw_odds(double draw_odds) {
        this.draw_odds = draw_odds;
    }

    public String getAway_team() {
        return away_team;
    }

    public void setAway_team(String away_team) {
        this.away_team = away_team;
    }

    public double getAway_team_odds() {
        return away_team_odds;
    }

    public void setAway_team_odds(double away_team_odds) {
        this.away_team_odds = away_team_odds;
    }

    public long getStart_time_delay() {
        return start_time_delay;
    }

    public void setStart_time_delay(long start_time_delay) {
        this.start_time_delay = start_time_delay;
    }

    public long getMatch_duration() {
        return match_duration;
    }

    public void setMatch_duration(long match_duration) {
        this.match_duration = match_duration;
    }
}
