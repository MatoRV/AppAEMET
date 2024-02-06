package com.example.aplicacionaemet.Controller;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.View.MainActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainController {

    private static final String URL = "https://opendata.aemet.es/opendata/api/prediccion/especifica/municipio/diaria/";

    private static MainController mySingleController;

    private List<Tiempo> dataRequested;

    private TiempoListViewModel myViewModel;

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
        if (array == null || context == null) {
            return;
        }

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
        spinner.setSelection(0,false);
    }

    public void itemSelected(Spinner spinner, String[] array, TextView textView) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String cp = "", cm = "", municipio;
                String seleccion = spinner.getSelectedItem().toString();
                for (int j = 0; j < array.length; j++) {
                    if (array[j].split(";")[3].equals(seleccion)) {
                        cp = array[j].split(";")[1];
                        cm = array[j].split(";")[2];
                        break;
                    }
                }
                textView.setText(seleccion);
                municipio = cp + cm;
                TiempoListViewModel tp = new TiempoListViewModel();
                tp.setMunicipio(municipio);
                //requestDataFromHttp(municipio);
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
        if (array == null) {
            return;
        }
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
    public void requestDataFromHttp(String municipio, TiempoListViewModel myViewModel) {
        Peticion p = new Peticion();
        p.requestData(URL, municipio);

        this.myViewModel = myViewModel;
    }

    public void requestTiempoData(String Url) {
        Log.d("Peticion","Url tiempo: "+Url);
        Peticion p = new Peticion();
        p.requestDataTiempo(Url);
    }

    // Es llamado cuando onResponse está correcto
    public void setDataFromHttp(String json) {
        Respuesta r = new Respuesta(json);
        Log.d("Peticion","JSON setDataFromHttp: "+json);
        enlace = r.getUrlData();

    }

    public void setTiempoData(String json) {
        Respuesta r = new Respuesta(json);
        dataRequested = r.getTiempoData();
        if (myViewModel != null) myViewModel.setData(dataRequested);
    }

    public void setErrorFromHttp(String error) {
        MainController.activeActivity.errorData(error);
    }

    public static void setActivity(MainActivity myAct) {
        activeActivity = myAct;
    }

    public List<Tiempo> getList() {
        return this.dataRequested;
    }
}
