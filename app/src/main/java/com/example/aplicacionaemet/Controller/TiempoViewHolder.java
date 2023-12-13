package com.example.aplicacionaemet.Controller;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicacionaemet.R;

public class TiempoViewHolder extends RecyclerView.ViewHolder {

    TiempoAdapter mAdapter;

    private ImageView ivTiempo;

    private TextView tvDia;

    private TextView tvMax;

    private TextView tvMin;

    public TiempoViewHolder(View itemView, TiempoAdapter adapter) {
        super(itemView);

        ivTiempo = itemView.findViewById(R.id.img_tiempo);
        tvDia = itemView.findViewById(R.id.tv_dia);
        tvMax = itemView.findViewById(R.id.tv_tempMax);
        tvMin = itemView.findViewById(R.id.tv_tempMin);
        this.mAdapter = adapter;
    }

    public void setIvTiempo(Drawable data) {
        ivTiempo.setImageDrawable(data);
    }

    public void setTvDia(String data) {
        tvDia.setText(data);
    }

    public void setTvMax(String data) {
        tvMax.setText(data);
    }

    public void setTvMin(String data) {
        tvMin.setText(data);
    }
}
