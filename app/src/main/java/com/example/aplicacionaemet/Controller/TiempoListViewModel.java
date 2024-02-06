package com.example.aplicacionaemet.Controller;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.aplicacionaemet.Model.Tiempo;

import java.util.List;

public class TiempoListViewModel extends ViewModel {

    private MutableLiveData<List<Tiempo>> listaTiempo;

    private String municipio;

    public LiveData<List<Tiempo>> getTiempos() {
        if (listaTiempo == null) {
            listaTiempo = new MutableLiveData<List<Tiempo>>();
            loadTiempo();
        }
        return listaTiempo;
    }

    private void loadTiempo() {
        MainController.getSingleton().requestDataFromHttp(municipio,this);
        listaTiempo.postValue(MainController.getSingleton().getList());
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public void setData(List<Tiempo> list) {
        listaTiempo.postValue(list);
    }
}
