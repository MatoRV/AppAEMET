package com.example.aplicacionaemet.View;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionaemet.Controller.MainController;
import com.example.aplicacionaemet.Controller.TiempoAdapter;
import com.example.aplicacionaemet.Controller.TiempoListViewModel;
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

    private EditText editText;

    private TextView textView;

    private static MainActivity myActiveActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.spinner);

        stringArray = getResources().getStringArray(R.array.localidades_data);

        MainController.getSingleton().setupSpinner(spinner,stringArray,this);

        editText = findViewById(R.id.editText);

        TiempoListViewModel vmodel = new ViewModelProvider(this).get(TiempoListViewModel.class);
        vmodel.getTiempos().observe(this, tiempos -> {

            //update UI
            mRecyclerView = findViewById(R.id.rv_tiempo);
            mAdapter = new TiempoAdapter(this, mList);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        });

        textView = findViewById(R.id.tv_municipio);

        MainController.getSingleton().itemSelected(spinner,stringArray,textView);

        MainController.getSingleton().editTextFiltro(editText, stringArray, spinner);

        MainActivity.myActiveActivity = this;
        MainController.setActivity(this);
    }

    public void accessData() {

        List<Tiempo> lista = MainController.getSingleton().getDataRequested();

        mList.clear();
        for (Tiempo tiempo : lista) {
            mList.add(tiempo);
        }

        mAdapter.notifyDataSetChanged();
    }

    public void errorData(String error) {
        TextView tv = (TextView) findViewById(R.id.tv_municipio);
        tv.setText(error);
    }
}