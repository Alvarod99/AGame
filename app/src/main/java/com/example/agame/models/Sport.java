package com.example.agame.models;

public class Sport {
    private String key;
    private String group;
    private String title;
    private String description;
    private Boolean active;
    private Boolean has_outrights;

    public String getTitle() {
        return title;
    }

    public Boolean getHas_outrights() {
        return has_outrights;
    }

    public Boolean getActive() {
        return active;
    }

    public String getDescription() {
        return description;
    }


    public String getKey() {
        return key;
    }


    public String getGroup() {
        return group;
    }
}

