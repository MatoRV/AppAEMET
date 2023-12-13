package com.example.aplicacionaemet.Controller;

import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.View.MainActivity;

import java.util.LinkedList;
import java.util.List;

public class MainController {

    private static final String URL = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/";

    private static MainController mySingleController;

    private List<Tiempo> dataRequested;

    private static MainActivity activeActivity;

    private MainController() {
        dataRequested = new LinkedList<>();
    }

    public static MainController getSingleton() {
        if (MainController.mySingleController == null) {
            mySingleController = new MainController();
        }
        return mySingleController;
    }

    // Envia los datos al View
    public List<Tiempo> getDataFromHttp() {
        return this.dataRequested;
    }

    // Es llamado en el View
    public void requestDataFromHttp() {
        Peticion p = new Peticion();
        p.requestData(URL);
    }

    // Es llamado cuando onResponse está correcto
    public void setDataFromHttp(String json) {

    }
}
