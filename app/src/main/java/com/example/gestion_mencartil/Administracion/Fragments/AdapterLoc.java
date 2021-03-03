package com.example.gestion_mencartil.Administracion.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_mencartil.Administracion.Holder;
import com.example.gestion_mencartil.R;
import com.example.gestion_mencartil.Usuario.Models.Shops;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterLoc extends  RecyclerView.Adapter<Holder>{
    private FirebaseUser firebaseUser = null;
    private FirebaseDatabase database = null;
    private ArrayList<Shops> listaTiendas;
    Context context;
    Bundle b = Bundle.EMPTY;
    public AdapterLoc(Context context ) {
        this.context = context;
        listaTiendas = new ArrayList();
    }
    public AdapterLoc(ArrayList<Shops> listaTiendas ) {
        this.listaTiendas = listaTiendas;
        notifyItemInserted(listaTiendas.size());
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.row_loc, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.getName().setText(listaTiendas.get(position).getNameShop());
        holder.getRow().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Tienda pulsada");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTiendas.size();
    }
}
