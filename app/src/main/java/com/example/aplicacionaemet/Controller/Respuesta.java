package com.example.aplicacionaemet.Controller;

import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Respuesta {

    protected String datos;

    public Respuesta(String entrada) {
        datos = entrada;
    }

    public String getUrlData() {

        String datosEnlace;

        try {

            ObjectMapper om = new ObjectMapper();

            JsonNode node;

            node = om.readTree(this.datos);


            datosEnlace = node.get("datos").asText();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return datosEnlace;
    }

    public List<Tiempo> getTiempoData() {

        LinkedList<Tiempo> dataList = new LinkedList<>();

        try {
            SimpleDateFormat fechaRespuesta = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
            SimpleDateFormat fechaADevolver = new SimpleDateFormat("EEEE", new Locale("es", "ES"));

            ObjectMapper om = new ObjectMapper();
            JsonNode node = om.readTree(this.datos);

            JsonNode pronostico = node.get(0);
            JsonNode dias = pronostico.get("prediccion").get("dia");

            for (JsonNode dia : dias) {
                String obtenerFecha = dia.get("fecha").asText();
                Date fechaObtenida = fechaRespuesta.parse(obtenerFecha);
                String fecha = fechaADevolver.format(fechaObtenida);

                String estadoCielo = "";

                JsonNode estadoCieloValor = dia.get("estadoCielo");
                if (estadoCieloValor != null && estadoCieloValor.isArray() && estadoCieloValor.size() > 0) {
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
            throw new RuntimeException("Error al procesar JSON: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return dataList;
    }
}
