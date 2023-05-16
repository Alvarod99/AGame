package com.example.agame.models;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String id;
    private String sport_key;
    private String sport_title;
    private String commence_time;
    private String home_team;
    private String away_team;

    private List<Bookmaker> bookmakers;

    public String getId() {
        return id;
    }

    public String getSport_key() {
        return sport_key;
    }

    public String getSport_title() {
        return sport_title;
    }

    public String getCommence_time() {
        return commence_time;
    }

    public String getHome_team() {
        return home_team;
    }

    public String getAway_team() {
        return away_team;
    }

    public List<Bookmaker> getBookmakers() {
        return bookmakers;
    }
}