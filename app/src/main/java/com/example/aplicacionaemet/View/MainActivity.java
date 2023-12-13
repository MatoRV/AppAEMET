package com.example.aplicacionaemet.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aplicacionaemet.Controller.MainController;
import com.example.aplicacionaemet.Controller.TiempoAdapter;
import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.R;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinkedList<Tiempo> mList = new LinkedList<>();

    private RecyclerView mRecyclerView;

    private TiempoAdapter mAdapter;

    private Spinner spinner;

    private static MainActivity myActiveActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSpinner();

        mRecyclerView = findViewById(R.id.rv_tiempo);

        mAdapter = new TiempoAdapter(this, mList);

        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    private void setupSpinner() {
        spinner = findViewById(R.id.spinner);
        String[] stringArray = getResources().getStringArray(R.array.localidades_data);
        String[] localidadesArray = new String[stringArray.length];
        for (int i = 0; i < localidadesArray.length; i++) {
            String linea = stringArray[i];
            String localidad = linea.split(";")[3];
            localidadesArray[i] = localidad;
        }
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, localidadesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void accessData() {

        List<Tiempo> lista = MainController.getSingleton().getDataFromHttp();

        mList.clear();
        for (Tiempo tiempo : lista) {
            mList.add(tiempo);
        }

        mAdapter.notifyDataSetChanged();
    }

    public void errorData(String error) {

    }
}