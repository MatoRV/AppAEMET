package com.example.aplicacionaemet.Controller;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.View.MainActivity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
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

    public void setupSpinner(Spinner spinner, String[] array, Context context) {
        String[] localidadesArray = new String[array.length];
        for (int i = 0; i < localidadesArray.length; i++) {
            String linea = array[i];
            String localidad = linea.split(";")[3];
            localidadesArray[i] = localidad;
        }
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, localidadesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void itemSelected(Spinner spinner, String[] array) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cp, cm, municipio;
                cp = array[i].split(";")[1];
                cm = array[i].split(";")[2];
                municipio = cp + cm;
                requestDataFromHttp(municipio);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void editTextFiltro(EditText editText, String[] array, Spinner spinner) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filtroSpinner(charSequence.toString(), array, spinner);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void filtroSpinner(String textoFiltrado, String[] array, Spinner spinner) {
        List<String> listaFiltro = new ArrayList<>();

        for (String linea : array) {
            String localidad = linea.split(";")[3];

            if (localidad.toLowerCase().contains(textoFiltrado.toLowerCase())) {
                listaFiltro.add(localidad);
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(spinner.getContext(),
                android.R.layout.simple_spinner_item, listaFiltro);
        spinner.setAdapter(adapter);
    }

    // Devuelve el enlace
    public String getDataFromHttp() {
        Log.d("Peticion", "Enlace: " + enlace);
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

    @RequiresApi(api = Build.VERSION_CODES.O)
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
