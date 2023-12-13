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

    private static final String API_KEY = "?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWN0b3JtYXRvcjVAZ21haWwuY29tIiwianRpIjoiMTdkZTUwNzYtNzEwMy00MzcyLTkyYWItYzEyM2FlYzcxMzZhIiwiaXNzIjoiQUVNRVQiLCJpYXQiOjE3MDEzNzMyOTcsInVzZXJJZCI6IjE3ZGU1MDc2LTcxMDMtNDM3Mi05MmFiLWMxMjNhZWM3MTM2YSIsInJvbGUiOiIifQ.rBKbaWEEK3tA7ponxThxFTWGDvJPiRgE5m25fbwsv1Q";

    public Peticion() {

    }

    public void requestData(String URL, String municipio) {
        OkHttpClient cliente = new OkHttpClient();
        // Construye la peticion
        Request peticion = new Request.Builder()
                .url(URL + municipio + API_KEY)
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
