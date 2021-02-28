package com.example.gestion_mencartil.Fragmens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gestion_mencartil.Models.User;
import com.example.gestion_mencartil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FragmentCuenta extends Fragment {

    private DatabaseReference myRef = null;
    private FirebaseUser firebaseUser = null;
    private FirebaseDatabase database = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_cuenta, container, false);
        //Ids de los text view...
        datos();
        return vista;
    }

    public void datos(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();

        System.out.println("Cuenta del ususario/a "+firebaseUser.getEmail()+" con uid "+firebaseUser.getUid());


        myRef = database.getReference("Users");

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot datasnapshot,  String previousChildName) {
                User u = datasnapshot.getValue(User.class);
                if(u != null){
                    if(u.getUid().equals(firebaseUser.getUid())){
                        System.out.println("Alias "+u.getName()+" Email "+u.getEmail()+" Saldo "+u.getsaldo());
                        //Carga los datos del usuario en los text view..
                    }
                }
            }
            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}