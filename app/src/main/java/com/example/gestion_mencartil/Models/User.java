package com.example.gestion_mencartil.Models;

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
}
