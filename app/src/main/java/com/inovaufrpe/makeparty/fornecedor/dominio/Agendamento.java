package com.inovaufrpe.makeparty.fornecedor.dominio;

import com.inovaufrpe.makeparty.cliente.dominio.Customer;

import java.util.Date;

public class Agendamento {
    private Date dateInicio;
    private Date dateFim;
    private Ad anuncio;
    private Customer customer;
    private String situacao;

    public Agendamento(Date dateInicio, Date dateFim, Ad anuncio, Customer customer, String situacao) {
        this.dateInicio = dateInicio;
        this.dateFim = dateFim;
        this.anuncio = anuncio;
        this.customer = customer;
        this.situacao = situacao;
    }

    public Agendamento() {
    }

    public Date getDateInicio() {
        return dateInicio;
    }

    public void setDateInicio(Date dateInicio) {
        this.dateInicio = dateInicio;
    }

    public Date getDateFim() {
        return dateFim;
    }

    public void setDateFim(Date dateFim) {
        this.dateFim = dateFim;
    }

    public Ad getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Ad anuncio) {
        this.anuncio = anuncio;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
