package com.inovaufrpe.makeparty.cliente.dominio;

import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;

import java.util.ArrayList;
import java.util.List;

public class Wishlists {

    public List<Ad> ads;
    //public List<Ad> ads;
    private Customer nomesocial;

    public List<Ad> getAd() {
        return ads;
    }
    public void setAd(List<Ad> ads) {
        this.ads = ads;
    }

    public Customer getNomesocial() {
        return nomesocial;
    }

    public void setNomesocial(Customer nomesocial) {
        this.nomesocial = nomesocial;
    }

}
