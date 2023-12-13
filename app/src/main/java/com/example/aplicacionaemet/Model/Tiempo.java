package com.example.aplicacionaemet.Model;

import android.graphics.drawable.Drawable;

public class Tiempo {

    private Drawable tiempo;

    private String dia;

    private String tempMax;

    private String tempMin;

    public Tiempo(Drawable tiempo, String dia, String tempMax, String tempMin) {
        this.tiempo = tiempo;
        this.dia = dia;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public Drawable getTiempo() {
        return tiempo;
    }

    public String getDia() {
        return dia;
    }

    public String getTempMax() {
        return tempMax;
    }

    public String getTempMin() {
        return tempMin;
    }
}
