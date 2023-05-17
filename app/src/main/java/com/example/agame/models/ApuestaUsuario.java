package com.example.agame.models;

import android.annotation.SuppressLint;
import android.widget.Button;

public class ApuestaUsuario{
    String id, id_partido;
    Double ganancias;
    Double price;
    String Resultado;

    public ApuestaUsuario(String id, String id_partido, Double ganancias, Double price, String Resultado){
        this.id = id;
        this.id_partido = id_partido;
        this.ganancias = ganancias;
        this.price = price;
        this.Resultado = Resultado;
    }

    public String getResultado() {
        return Resultado;
    }

    public String getId() {
        return id;
    }

    public String getId_partido() {
        return id_partido;
    }

    public Double getGanancias() {
        return ganancias;
    }

    public Double getPrice() {
        return price;
    }
}
