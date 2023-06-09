package com.example.agame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;

public class RowPartidos {
    private String sport_title, home_team,away_team;
    private Double price1, priceX, price2;
    String id;

    public RowPartidos(String sport_title, String home_team,
                       String away_team, String id,Double price1, Double price2, Double priceX){
        this.sport_title = sport_title;
        this.home_team = home_team;
        this.away_team = away_team;
        this.price1 = price1;
        this.price2 = price2;
        this.priceX = priceX;
        this.id = id;

    }

    public String getId(){
        return id;
    }

    public String getSport_title(){
        return sport_title;
    }

    public void setSport_title(String sport_title){
        this.sport_title =sport_title;
    }

    public String getHome_team(){
        return home_team;
    }

    public void setHome_team(String home_team){
        this.home_team=home_team;
    }

    public String getAway_team(){
        return away_team;
    }

    public void setAway_team(String away_team){
        this.away_team =away_team;
    }

    public Double getPrice1(){
        return price1;
    }

    public void setPrice1(Double price1){
        this.price1 =price1;
    }

    public Double getPrice2(){
        return price2;
    }

    public void setPrice2(Double price2){
        this.price2 =price2;
    }

    public Double getPriceX(){
        return priceX;
    }

    public void setPriceX(Double priceX){
        this.priceX =priceX;
    }

}
