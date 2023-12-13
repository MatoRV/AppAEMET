package com.example.aplicacionaemet.Controller;

import com.example.aplicacionaemet.Model.Tiempo;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.LinkedList;
import java.util.List;

public class Respuesta {

    protected String datos;

    public Respuesta(String entrada) {
        datos = entrada;
    }

    public List<Tiempo> getTiempoData() {

        LinkedList<Tiempo> dataList = new LinkedList<>();

        JsonElement jsonElement = JsonParser.parseString(this.datos);
    }
}
