package com.inovaufrpe.makeparty.fornecedor.dominio;

import java.util.Date;

public class Evento {
    private Owner owner;
    private Date dateInicio;
    private Date dateFim;
    private String obs;
    private String tipoEvento;
    private String nomeCliente;
    private String endereco;

    public Evento(Owner owner, Date dateInicio, Date dateFim, String obs, String tipoEvento, String nomeCliente, String endereco) {
        this.owner = owner;
        this.dateInicio = dateInicio;
        this.dateFim = dateFim;
        this.obs = obs;
        this.tipoEvento = tipoEvento;
        this.nomeCliente = nomeCliente;
        this.endereco = endereco;
    }

    public Evento() {
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getTipoEvento() {
        return tipoEvento;
    }

    public void setTipoEvento(String tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}
