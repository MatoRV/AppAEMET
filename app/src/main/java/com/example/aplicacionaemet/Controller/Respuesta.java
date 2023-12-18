package com.example.aplicacionaemet.Controller;

import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.LinkedList;
import java.util.List;

public class Respuesta {

    protected String datos;

    public Respuesta(String entrada) {
        datos = entrada;
    }

    public String getUrlData() {
        
        String datosEnlace = null;

        // Parsea el JSON a un objeto JsonObject
        JsonObject jsonObject = JsonParser.parseString(this.datos).getAsJsonObject();

        // Obtiene el elemento datos
        JsonElement datosElm = jsonObject.get("datos");
        
        // Verifica si es distinto de nulo
        if (datosElm != null) {
            datosEnlace = datosElm.getAsString();
        }
        
        return datosEnlace;
    }

    public List<Tiempo> getTiempoData() {

        String valorCielo = "0";
        String tempMax = "0";
        String tempMin = "0";

        LinkedList<Tiempo> dataList = new LinkedList<>();

        JsonElement jsonElement = JsonParser.parseString(getUrlData());

        JsonObject jsonObject = jsonElement.getAsJsonObject().get("prediccion").getAsJsonObject();

        JsonArray diasArray = jsonObject.getAsJsonArray("dia");

        for (JsonElement dia : diasArray) {



            int imgTiempo = 0;
            // Obtiene estadoCielo
            JsonArray estadoCieloArray = dia.getAsJsonObject().getAsJsonArray("estadoCielo");
            JsonArray temperaturaArray = dia.getAsJsonObject().getAsJsonArray("temperatura");
            String fecha = dia.getAsJsonObject().get("fecha").getAsString();
            if (estadoCieloArray.size() > 0) {
                JsonObject estadoCielo = estadoCieloArray.get(0).getAsJsonObject();
                valorCielo = estadoCielo.get("value").getAsString();
            }

            if (temperaturaArray.size() > 0) {
                JsonObject temperaturaMax = temperaturaArray.get(0).getAsJsonObject();
                JsonObject temperaturaMin = temperaturaArray.get(1).getAsJsonObject();
                tempMax = temperaturaMax.get("maxima").getAsString();
                tempMin = temperaturaMin.get("minima").getAsString();
            }

            if (Integer.parseInt(valorCielo) == 0) {
                imgTiempo = R.drawable.weather_sunny;
            } else if (Integer.parseInt(valorCielo) > 11 && Integer.parseInt(valorCielo) <= 16) {
                imgTiempo = R.drawable.weather_partly_cloudy;
            } else if (Integer.parseInt(valorCielo) > 16 && Integer.parseInt(valorCielo) <= 24) {
                imgTiempo = R.drawable.weather_cloudy;
            } else if (Integer.parseInt(valorCielo) > 24 && Integer.parseInt(valorCielo) <= 43) {
                imgTiempo = R.drawable.weather_rainy;
            } else if (Integer.parseInt(valorCielo) > 43 && Integer.parseInt(valorCielo) <= 65) {
                imgTiempo = R.drawable.weather_pouring;
            } else {
                imgTiempo = R.drawable.weather_lightning_rainy;
            }
            dataList.add(new Tiempo(imgTiempo,fecha,tempMax,tempMin));
        }

        return dataList;
    }
}
