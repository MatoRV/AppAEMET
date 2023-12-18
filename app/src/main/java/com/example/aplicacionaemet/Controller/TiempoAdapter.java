package com.example.aplicacionaemet.Controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionaemet.Model.Tiempo;
import com.example.aplicacionaemet.R;

import java.util.LinkedList;

public class TiempoAdapter extends RecyclerView.Adapter<TiempoViewHolder> {

    private final LinkedList<Tiempo> mList;

    private LayoutInflater mInflater;

    public TiempoAdapter(Context context, LinkedList<Tiempo> list) {
        mInflater = LayoutInflater.from(context);
        this.mList = list;
    }

    @NonNull
    @Override
    public TiempoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.lista_tiempo,
                parent, false);
        return new TiempoViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TiempoViewHolder holder, int position) {
        holder.setIvTiempo(this.mList.get(position).getTiempo());
        holder.setTvDia(this.mList.get(position).getDia().toUpperCase());
        holder.setTvMax("Max: " + this.mList.get(position).getTempMax() + "ºC");
        holder.setTvMin("Min: " + this.mList.get(position).getTempMin() + "ºC");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
