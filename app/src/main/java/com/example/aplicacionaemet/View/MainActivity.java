package com.example.aplicacionaemet.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.aplicacionaemet.R;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupSpinner();
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
}