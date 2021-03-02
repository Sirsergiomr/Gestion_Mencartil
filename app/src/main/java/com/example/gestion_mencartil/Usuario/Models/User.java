package com.example.gestion_mencartil.Usuario.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User{
    private String uid;
    private String email;
    private String name;
    private String saldo;
    public User() {
    }

    public User(String uid, String email, String name,String saldo) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.saldo = saldo;
    }
    public User(String uid, String email, String name) {
        this.uid = uid;
        this.email = email;
        this.name = name;
    }
    public String getsaldo() {
        return saldo;
    }

    public void setPhotoUrl(String photoUrl) {
        saldo = photoUrl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email",email);
        result.put("name",name);
        result.put("saldo",saldo);
        result.put("uid", uid);

        return result;
    }
}
