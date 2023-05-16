package com.example.agame.models;

import java.util.List;

public class Bookmaker {
    private String key;
    private String title;
    private String last_update;
    private List<Market> markets;

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public String getLast_update() {
        return last_update;
    }

    public List<Market> getMarkets() {
        return markets;
}
}