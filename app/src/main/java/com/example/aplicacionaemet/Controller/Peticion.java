package com.example.aplicacionaemet.Controller;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http2.Http2Reader;

public class Peticion {

    public Peticion() {

    }

    public void requestData(String URL) {
        OkHttpClient cliente = new OkHttpClient();
        // Construye la peticion
        Request peticion = new Request.Builder()
                .url(URL)
                .get()
                .addHeader("cache-control","no-cache")
                .build();
        // Realiza una llamada al sercer, pero en otro thread
        Call llamada = cliente.newCall(peticion);

        llamada.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                String respuesta = e.getMessage();
                Handler manejador = new Handler(Looper.getMainLooper());

                manejador.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

            }
        });
    }
}
