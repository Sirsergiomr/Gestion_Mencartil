package com.example.gestion_mencartil.ui.main;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gestion_mencartil.MainActivity;
import com.example.gestion_mencartil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class LoginRegistroUsuarios extends AppCompatActivity implements View.OnClickListener {

    private EditText TextEmail;
    private EditText TextPassword;
    private Button btnRegistrar, btnLogin;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_usuarios);

        firebaseAuth = FirebaseAuth.getInstance();

        TextEmail = (EditText) findViewById(R.id.editEmail);
        TextPassword = (EditText) findViewById(R.id.editPassword);

        btnRegistrar = (Button) findViewById(R.id.RegisterButton);
        btnLogin = (Button) findViewById(R.id.LoginButton);

        progressDialog = new ProgressDialog(this);

        btnRegistrar.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
    }

    private void registrarUsuario() {

        String email = TextEmail.getText().toString().trim();
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

        String email = TextEmail.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();

        if (email.equals("")) {
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (password.equals("")) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando loguin...");
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(LoginRegistroUsuarios.this, MainActivity.class));
                            Toast.makeText(LoginRegistroUsuarios.this, "Se ha logueado el usuario con el email: " + TextEmail.getText(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(LoginRegistroUsuarios.this, "No se pudo loguear el usuario ", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.RegisterButton:
                registrarUsuario();
                break;
            case R.id.LoginButton:
                loguearUsuario();
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


}
