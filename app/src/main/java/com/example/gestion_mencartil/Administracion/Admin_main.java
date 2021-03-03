package com.example.gestion_mencartil.Administracion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestion_mencartil.Administracion.Fragments.AdapterLoc;
import com.example.gestion_mencartil.R;
import com.example.gestion_mencartil.Usuario.Models.Shops;
import com.example.gestion_mencartil.ui.main.LoginRegistro;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static androidx.recyclerview.widget.RecyclerView.*;

public class Admin_main extends AppCompatActivity {
    private ArrayList<Shops> listaTiendas = new ArrayList<>();
    DatabaseReference myRef = null;
    FirebaseDatabase database = null;
    FirebaseUser firebaseUser = null;

    RecyclerView recyclerView;
    private Object LayoutManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
        recyclerView = findViewById(R.id.recyclerLoc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new AdapterLoc(this));
        LayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager((RecyclerView.LayoutManager) LayoutManager);
        //datos();
        Bundle bundle = getIntent().getExtras();
        String alias = bundle.getString("alias");
        if (alias != null) {
            setTitle(alias);
        }
    }


    public void  datos(){
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Shop");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( DataSnapshot datasnapshot,  String previousChildName) {
                Shops p = datasnapshot.getValue(Shops.class);
                if(p!=null){
                    System.out.println(" | " + p.getUid() + " | " + p.getNameShop());

                    listaTiendas.add(p);
                    recyclerView.setAdapter(new AdapterLoc(listaTiendas));
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {
        if (item.getItemId()==(R.id.sing_out)){
            FirebaseAuth.getInstance().signOut();

            SharedPreferences.Editor prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit();
            prefs.clear();
            prefs.apply();

            Intent i = new  Intent(this, LoginRegistro.class);
            startActivity(i);
        }

        return true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Sing out para salir ", Toast.LENGTH_LONG).show();
    }
}
