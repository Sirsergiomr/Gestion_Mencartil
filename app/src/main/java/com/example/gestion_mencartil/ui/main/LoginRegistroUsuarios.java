package com.example.gestion_mencartil.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_mencartil.MainActivity;
import com.example.gestion_mencartil.Models.User;
import com.example.gestion_mencartil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginRegistroUsuarios extends AppCompatActivity implements View.OnClickListener {

    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar, btnLogin;
    private ProgressDialog progressDialog;
    private FirebaseDatabase database;
    private FirebaseUser firebaseUser = null;
    String alias = "";
    ArrayList<User> Registro = new ArrayList<>();
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_usuarios);

        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        TextEmail = (EditText) findViewById(R.id.editEmail);
        TextPassword = (EditText) findViewById(R.id.editPassword);

        btnRegistrar = (Button) findViewById(R.id.RegisterButton);
        btnLogin = (Button) findViewById(R.id.LoginButton);

        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        bundle.putString("message","Entró");
        analytics.logEvent("InitScreen",bundle);
        session();
    }
    String email;
    private void registrarUsuario() {

        email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        if (email.equals("")) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.equals("")) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando registro...");
        progressDialog.show();

        if (isValidEmail(email) && validarContraseña()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                try {
                                    myRef = database.getReference("Users").child(firebaseUser.getUid());
                                    CargarArbolUsers(email);
                                }catch (NullPointerException e){
                                    GenerarArbolUsers(Registro, email );
                                }
                                persistencia();
                                session();
                                Toast.makeText(LoginRegistroUsuarios.this, "Se ha registrado el usuario con el email: " + TextEmail.getText(), Toast.LENGTH_LONG).show();
                            } else {
                                if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                    Toast.makeText(LoginRegistroUsuarios.this, "El usuario ya existe ", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(LoginRegistroUsuarios.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                                }
                            }
                            progressDialog.dismiss();
                        }
                    });
        } else {
            Toast.makeText(LoginRegistroUsuarios.this, "El correo o la contraseña son incorrectos. La contraseña debe de ser mayor de 5 y menor de 21 caracteres", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    private void loguearUsuario() {

        email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();
        if(email.isEmpty()|| password.isEmpty()){
            if (email.equals("")) {
                Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
                return;
            }

            if (password.equals("")) {
                Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
                return;
            }
        }else{
            progressDialog.setMessage("Realizando loguin...");
            progressDialog.show();


            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(LoginRegistroUsuarios.this, "Se ha logueado el usuario con el email: " + TextEmail.getText(), Toast.LENGTH_LONG).show();
                                persistencia();
                                session();
                            } else {
                                Toast.makeText(LoginRegistroUsuarios.this, "No se pudo loguear el usuario ", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.RegisterButton:
                registrarUsuario();
                persistencia();
                break;
            case R.id.LoginButton:
                loguearUsuario();
                persistencia();
                break;
        }
    }

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validarContraseña() {
        String contraseña;
        contraseña = TextPassword.getText().toString();
        if (contraseña.length() >= 6 && contraseña.length() <= 20) {
            return true;
        } else return false;
    }

    private void persistencia(){
        SharedPreferences.Editor prefs = getSharedPreferences(
                getString(R.string.prefs_file),
                Context.MODE_PRIVATE
        ).edit();
        prefs.putString("email", email);
        prefs.apply();
    }
    boolean existe = false;
    public void GenerarArbolUsers(ArrayList<User> arrayList, String email){
        firebaseUser =  firebaseAuth.getCurrentUser();
        for(int i = 0 ;i <arrayList.size();i++){
            if (firebaseUser.getUid().equals(arrayList.get(i).getUid())){
                existe = true;
            }
        }

        if(existe == false){
            for (int i = 0; i<email.length(); i++){
                if(email.charAt(i) == '@'){
                    break;
                }else{
                    alias = alias + email.charAt(i);
                }
            }

            System.out.println("Alias = "+ alias);

            User u = new User(firebaseUser.getUid(),email,alias,"0");
            database.getReference("Users").child(firebaseUser.getUid()).setValue(u);
        }
    }

    public void CargarArbolUsers(String  email){
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot datasnapshot) {
                    Registro.clear();
                    for (DataSnapshot snapshot : datasnapshot.getChildren()){
                        User u = datasnapshot.getValue(User.class);
                        if (u.getName()!= null){
                            Registro.add(u);
                        }
                    }
                    GenerarArbolUsers(Registro, email);
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.e("onDataChange", "Error en OndataChange User", error.toException());
                }
            });
    }

    private void session(){
        SharedPreferences prefs = getSharedPreferences(
                getString(R.string.prefs_file),
                Context.MODE_PRIVATE
        );
        email = prefs.getString("email",null);
        if(email == null){
            btnLogin.setEnabled(true);
            btnRegistrar.setEnabled(true);
        }else{
            System.out.println("Email = "+email+"----------------------------------------------------------------------------------------");
            btnLogin.setEnabled(false);
            btnRegistrar.setEnabled(false);
            alias="";
            for (int i = 0; i<email.length(); i++){
                if(email.charAt(i) == '@'){
                    break;
                }else{
                    alias = alias + email.charAt(i);
                }
            }
            GoHome(alias);
        }

    }
    public void GoHome(String alias){
        Intent i = new Intent(LoginRegistroUsuarios.this, MainActivity.class);
        i.putExtra("alias", alias);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        session();
    }
}
