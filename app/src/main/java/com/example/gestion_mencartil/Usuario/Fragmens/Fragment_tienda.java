package com.example.gestion_mencartil.Usuario.Fragmens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gestion_mencartil.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_tienda#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_tienda extends Fragment {

    public static Fragment_tienda newInstance(String param1, String param2) {
        Fragment_tienda fragment = new Fragment_tienda();
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
        return inflater.inflate(R.layout.fragment_tienda, container, false);
    }
}