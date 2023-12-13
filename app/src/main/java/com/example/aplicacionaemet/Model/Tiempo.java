package com.example.aplicacionaemet.Model;

public class Tiempo {

    private String dia;

    private String tempMax;

    private String tempMin;

    public Tiempo(String dia, String tempMax, String tempMin) {
        this.dia = dia;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
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
