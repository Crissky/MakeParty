package com.inovaufrpe.makeparty.fornecedor.dominio;

import java.util.ArrayList;

public class HorariosDisponiveis {
    private ArrayList segunda;
    private ArrayList terca;
    private ArrayList quarta;
    private ArrayList quinta;
    private ArrayList sexta;
    private ArrayList sabado;
    private ArrayList domingo;

    public HorariosDisponiveis(ArrayList segunda, ArrayList terca, ArrayList quarta, ArrayList quinta, ArrayList sexta, ArrayList sabado, ArrayList domingo) {
        this.segunda = segunda;
        this.terca = terca;
        this.quarta = quarta;
        this.quinta = quinta;
        this.sexta = sexta;
        this.sabado = sabado;
        this.domingo = domingo;
    }

    public HorariosDisponiveis() {
    }

    public ArrayList getSegunda() {
        return segunda;
    }

    public void setSegunda(ArrayList segunda) {
        this.segunda = segunda;
    }

    public ArrayList getTerca() {
        return terca;
    }

    public void setTerca(ArrayList terca) {
        this.terca = terca;
    }

    public ArrayList getQuarta() {
        return quarta;
    }

    public void setQuarta(ArrayList quarta) {
        this.quarta = quarta;
    }

    public ArrayList getQuinta() {
        return quinta;
    }

    public void setQuinta(ArrayList quinta) {
        this.quinta = quinta;
    }

    public ArrayList getSexta() {
        return sexta;
    }

    public void setSexta(ArrayList sexta) {
        this.sexta = sexta;
    }

    public ArrayList getSabado() {
        return sabado;
    }

    public void setSabado(ArrayList sabado) {
        this.sabado = sabado;
    }

    public ArrayList getDomingo() {
        return domingo;
    }

    public void setDomingo(ArrayList domingo) {
        this.domingo = domingo;
    }
}
