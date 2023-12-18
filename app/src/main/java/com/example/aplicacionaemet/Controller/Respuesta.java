package com.example.aplicacionaemet.Controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Respuesta {

    protected String datos;

    public Respuesta(String entrada) {
        datos = entrada;
    }

    public String getUrlData() throws JsonProcessingException {

        String datosEnlace;

        ObjectMapper om = new ObjectMapper();

        JsonNode node = om.readTree(this.datos);

        datosEnlace = node.get("datos").asText();

        return datosEnlace;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public List<Tiempo> getTiempoData() {

        LinkedList<Tiempo> dataList = new LinkedList<>();

        try {
            DateTimeFormatter fechaRespuesta = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            DateTimeFormatter fechaADevolver = DateTimeFormatter.ofPattern("EEEE", new Locale("es", "ES"));

            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(this.datos);

            JsonNode pronostico = node.get(0);
            JsonNode dias = pronostico.get("prediccion").get("dia");

            for (JsonNode dia : dias) {
                String obtenerFecha = dia.get("fecha").asText();
                LocalDate fechaObtenida = LocalDate.parse(obtenerFecha, fechaRespuesta);
                String fecha = fechaADevolver.format(fechaObtenida);

                String estadoCielo = "";

                JsonNode estadoCieloValor = dia.get("estadoCielo");
                if (estadoCieloValor.isArray() && estadoCieloValor.size() > 0) {
                    estadoCielo = estadoCieloValor.get(0).get("value").asText();
                }

                String tempMax = dia.get("temperatura").get("maxima").asText();
                String tempMin = dia.get("temperatura").get("minima").asText();

                int imgTiempo;
                if (estadoCielo.equals("0") || estadoCielo.equals("")) {
                    imgTiempo = R.drawable.weather_sunny;
                } else if (Integer.parseInt(estadoCielo) > 11 && Integer.parseInt(estadoCielo) <= 16) {
                    imgTiempo = R.drawable.weather_partly_cloudy;
                } else if (Integer.parseInt(estadoCielo) > 16 && Integer.parseInt(estadoCielo) <= 24) {
                    imgTiempo = R.drawable.weather_cloudy;
                } else if (Integer.parseInt(estadoCielo) > 24 && Integer.parseInt(estadoCielo) <= 43) {
                    imgTiempo = R.drawable.weather_rainy;
                } else if (Integer.parseInt(estadoCielo) > 43 && Integer.parseInt(estadoCielo) <= 65) {
                    imgTiempo = R.drawable.weather_pouring;
                } else {
                    imgTiempo = R.drawable.weather_lightning_rainy;
                }


                dataList.add(new Tiempo(imgTiempo,fecha,tempMax,tempMin));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return dataList;
    }
}
