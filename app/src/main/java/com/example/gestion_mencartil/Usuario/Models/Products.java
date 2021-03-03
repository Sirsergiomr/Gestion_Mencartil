package com.example.gestion_mencartil.Usuario.Models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Products {
    private String namePro;
    private String description;
    private int quantity;
    private String price;
    private String subTotal;
    private String uid;
    public Products() {
    }

    public Products(String namePro, String description, int quantity,String price, String subTotal, String uid) {
        this.namePro = namePro;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.subTotal = subTotal;
        this.uid = uid;
    }

    public String getNamePro() {
        return namePro;
    }

    public void setNamePro(String namePro) {
        this.namePro = namePro;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("Nombre Producto",namePro);
        result.put("Descripcion",description);
        result.put("Cantidad",quantity);
        result.put("Precio", price);
        result.put("Subtotal", subTotal);
        result.put("Uid", uid);

        return result;
    }
}
