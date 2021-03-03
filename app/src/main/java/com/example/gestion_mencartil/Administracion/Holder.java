package com.example.gestion_mencartil.Administracion;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_mencartil.R;

public class Holder extends RecyclerView.ViewHolder{
    private TextView name;

    private ConstraintLayout row;


    public Holder(@NonNull View itemView) {
        super(itemView);
        this.name = itemView.findViewById(R.id.textViewnameLoc);
    }


    public ConstraintLayout getRow() {
        return row;
    }

    public TextView getName() {
        return name;
    }

    public void setRow(ConstraintLayout row) {
        this.row = row;
    }
}