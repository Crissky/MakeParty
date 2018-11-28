package com.inovaufrpe.makeparty.fornecedor.dominio;

import com.inovaufrpe.makeparty.cliente.dominio.PessoaFisica;

import java.util.Date;

public class Agendamento {
    private Date dateInicio;
    private Date dateFim;
    private Anuncio anuncio;
    private PessoaFisica pessoaFisica;
    private String situacao;

    public Agendamento(Date dateInicio, Date dateFim, Anuncio anuncio, PessoaFisica pessoaFisica, String situacao) {
        this.dateInicio = dateInicio;
        this.dateFim = dateFim;
        this.anuncio = anuncio;
        this.pessoaFisica = pessoaFisica;
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

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public PessoaFisica getPessoaFisica() {
        return pessoaFisica;
    }

    public void setPessoaFisica(PessoaFisica pessoaFisica) {
        this.pessoaFisica = pessoaFisica;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
