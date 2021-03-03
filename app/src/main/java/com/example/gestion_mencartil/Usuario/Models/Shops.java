package com.example.gestion_mencartil.Usuario.Models;

public class Shops {
    private String nameShop;
    private String uid;
    public Shops() {
    }

    public Shops(String nameShop, String uid) {
        this.nameShop = nameShop;
        this.uid = uid;
    }

    public String getNameShop() {
        return nameShop;
    }

    public void setNameShop(String nameShop) {
        this.nameShop = nameShop;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    
}
