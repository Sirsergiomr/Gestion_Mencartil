package com.example.gestion_mencartil.Usuario.Fragmens;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.gestion_mencartil.Usuario.Models.User;
import com.example.gestion_mencartil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class FragmentCuenta extends Fragment {

    private DatabaseReference myRef = null;
    private FirebaseUser firebaseUser = null;
    private FirebaseDatabase database = null;
    private TextView TxtEmail, TxtAlias, TxtSaldoTjt;
    private EditText SRecargaEditText;
    private Button btnRecarga;

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

        TxtEmail = (TextView) vista.findViewById(R.id.EmailTxtView);
        TxtAlias = (TextView) vista.findViewById(R.id.AliasTxtView);
        TxtSaldoTjt = (TextView) vista.findViewById(R.id.SaldoTxtView);
        btnRecarga = (Button) vista.findViewById(R.id.RecargaSaldoButton);
        SRecargaEditText = (EditText) vista.findViewById(R.id.SaldoRecargaEditText);
        btnRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SRecargaEditText.getText().toString().isEmpty()){
                }else{
                    try {
                        int cantidadRecarga = Integer.parseInt(SRecargaEditText.getText().toString());

                        update(cantidadRecarga);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

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
                        TxtEmail.setText(u.getEmail());
                        TxtAlias.setText(u.getName());
                        TxtSaldoTjt.setText(u.getsaldo());
                    }
                }
            }
            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void update(int saldo){
        myRef = database.getReference("Users");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot datasnapshot,  String previousChildName) {
                User u = datasnapshot.getValue(User.class);
                if(u != null){
                    if(u.getUid().equals(firebaseUser.getUid())){

                        int saldobase = Integer.parseInt(u.getsaldo());
                        int saldofinal = saldobase+saldo;

                        User upd = new User(firebaseUser.getUid(),u.getEmail(),u.getName(),Integer.toString(saldofinal));
                        Map<String, Object> UserValues = upd.toMap();
                        Map<String, Object> childUpdates = new HashMap<>();
                        childUpdates.put(firebaseUser.getUid(), UserValues);
                        myRef.updateChildren(childUpdates);

                        TxtSaldoTjt.setText(upd.getsaldo());
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