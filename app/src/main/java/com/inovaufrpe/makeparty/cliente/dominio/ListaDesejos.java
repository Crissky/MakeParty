package com.inovaufrpe.makeparty.cliente.dominio;

import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;

import java.util.ArrayList;

public class ListaDesejos {

    private ArrayList<Ads> ad;
    private PessoaFisica nomesocial;

    public ArrayList<Ads> getAd() {
        return ad;
    }

    public void setAd(ArrayList<Ads> ad) {
        this.ad = ad;
    }

    public PessoaFisica getNomesocial() {
        return nomesocial;
    }

    public void setNomesocial(PessoaFisica nomesocial) {
        this.nomesocial = nomesocial;
    }







}
