package com.inovaufrpe.makeparty.cliente.dominio;

import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;

import java.util.ArrayList;

public class Wishlists {

    private ArrayList<Ads> ad;
    private Customer nomesocial;

    public ArrayList<Ads> getAd() {
        return ad;
    }

    public void setAd(ArrayList<Ads> ad) {
        this.ad = ad;
    }

    public Customer getNomesocial() {
        return nomesocial;
    }

    public void setNomesocial(Customer nomesocial) {
        this.nomesocial = nomesocial;
    }







}
