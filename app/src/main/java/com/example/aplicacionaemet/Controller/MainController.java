package com.example.aplicacionaemet.Controller;

import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.View.MainActivity;

import java.util.LinkedList;
import java.util.List;

public class MainController {

    private static final String URL = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/";

    private static MainController mySingleController;

    private List<Tiempo> dataRequested;

    private String enlace;

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
    public String getDataFromHttp() {
        return this.enlace;
    }

    // Es llamado en el View
    public void requestDataFromHttp(String municipio) {
        Peticion p = new Peticion();
        p.requestData(URL, municipio);
    }

    // Es llamado cuando onResponse está correcto
    public void setDataFromHttp(String json) {
        Respuesta r = new Respuesta(json);
        enlace = r.getUrlData();
        MainController.activeActivity.accessData();
    }

    public void setErrorFromHttp(String error) {
        MainController.activeActivity.errorData(error);
    }

    public static void setActivity(MainActivity myAct) {
        activeActivity = myAct;
    }
}
