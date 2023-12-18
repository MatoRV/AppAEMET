package com.example.aplicacionaemet.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionaemet.Controller.MainController;
import com.example.aplicacionaemet.Controller.TiempoAdapter;
import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.R;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LinkedList<Tiempo> mList = new LinkedList<>();

    private RecyclerView mRecyclerView;

    private TiempoAdapter mAdapter;

    private Spinner spinner;

    private String[] stringArray;

    private String municipio;

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

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stringArray = getResources().getStringArray(R.array.localidades_data);
                String cp,cm;
                cp = stringArray[position].split(";")[1];
                cm = stringArray[position].split(";")[2];
                municipio = cp + cm;
                Log.d("Peticion","Municipio en Main: "+municipio);
                MainController.getSingleton().requestDataFromHttp(municipio);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                MainController.getSingleton().requestDataFromHttp(municipio);

            }
        });


        MainActivity.myActiveActivity = this;
        MainController.setActivity(this);
    }

    private void setupSpinner() {
        spinner = findViewById(R.id.spinner);
        stringArray = getResources().getStringArray(R.array.localidades_data);
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

        List<Tiempo> lista = MainController.getSingleton().getDataRequested();

        mList.clear();
        for (Tiempo tiempo : lista) {
            mList.add(tiempo);
        }

        mAdapter.notifyDataSetChanged();
        TextView tv = (TextView) findViewById(R.id.tv_municipio);
        tv.setText(MainController.getSingleton().getDataFromHttp());
    }

    public void errorData(String error) {
        TextView tv = (TextView) findViewById(R.id.tv_municipio);
        tv.setText(error);
    }
}