package com.example.aplicacionaemet.Controller;

import android.util.Log;

import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.View.MainActivity;
import com.fasterxml.jackson.core.JsonProcessingException;

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

    // Devuelve el enlace
    public String getDataFromHttp() {
        Log.d("Peticion","Enlace: "+enlace);
        return this.enlace;
    }

    public List<Tiempo> getDataRequested() {
        return this.dataRequested;
    }

    // Es llamado en el View y trae la id del municipio
    public void requestDataFromHttp(String municipio) {
        Peticion p = new Peticion();
        Log.d("Peticion","Municipio: "+municipio);
        p.requestData(URL, municipio);
    }

    public void requestTiempoData(String Url) {
        Log.d("Peticion","Url tiempo: "+Url);
        Peticion p = new Peticion();
        p.requestDataTiempo(Url);
    }

    // Es llamado cuando onResponse está correcto
    public void setDataFromHttp(String json) throws JsonProcessingException {
        Respuesta r = new Respuesta(json);
        Log.d("Peticion","JSON setDataFromHttp: "+json);
        enlace = r.getUrlData();
        MainController.activeActivity.accessData();
    }

    public void setTiempoData(String json) {
        Respuesta r = new Respuesta(json);
        dataRequested = r.getTiempoData();
        MainController.activeActivity.accessData();
    }

    public void setErrorFromHttp(String error) {
        MainController.activeActivity.errorData(error);
    }

    public static void setActivity(MainActivity myAct) {
        activeActivity = myAct;
    }
}
