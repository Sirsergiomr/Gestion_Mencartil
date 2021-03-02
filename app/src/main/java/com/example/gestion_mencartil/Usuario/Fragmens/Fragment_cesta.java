package com.example.gestion_mencartil.Usuario.Fragmens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gestion_mencartil.R;


public class Fragment_cesta extends Fragment {

    public static Fragment_cesta newInstance(String param1, String param2) {
        Fragment_cesta fragment = new Fragment_cesta();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cesta, container, false);
    }
}