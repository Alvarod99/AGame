package com.example.agame.models;

import java.util.ArrayList;
import java.util.List;

public class Market {
    private String key;
    private String last_update;
    private List<Outcome> outcomes;

    public String getKey() {
        return key;
    }

    public String getLast_update() {
        return last_update;
    }

    public List<Outcome> getOutcomes() {
        return outcomes;
}
}