package com.inovaufrpe.makeparty.usuario.gui.adapter;

import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FiltroAnuncioSelecionado {
    public static final FiltroAnuncioSelecionado instance = new FiltroAnuncioSelecionado();
    private final Map<String, Object> values = new HashMap<>();

    public void setAnunciosList(ArrayList<Ads> anunciosList) {
        setValor("FiltroAnuncioSelecionado.anunciosList", anunciosList);
    }

    public ArrayList<Ads> getAnunciosList() {
        return (ArrayList<Ads>) values.get("FiltroAnuncioSelecionado.anunciosList");
    }

    public Ads getAnuncioSelecionado() {
        return (Ads) values.get("sessao.AnuncioSelecionado");
    }

    public void setAnuncioSelecionado(Ads anuncioSelecionado) {
        setValor("sessao.AnuncioSelecionado", anuncioSelecionado);
    }

    public Ads getTipoAnuncioSelecionado() {
        return (Ads) values.get("sessao.AnuncioSelecionado");
    }

    public void setTipoAnuncioSelecionado(Ads anuncioSelecionado) {
        setValor("sessao.AnuncioSelecionado", anuncioSelecionado);
    }
    public String getDiaSelecionadoPeloClienteDisp(){
        return ((String) values.get("sessao.DiaSelecionadoPeloCliente"));
    }
    public void setDiaSelecionadoPeloClienteDisp(String dataSelecionadaPeloClienteDisp){
        setValor("sessao.DiaSelecionadoPeloClienteDisp", dataSelecionadaPeloClienteDisp);

    }
    private void setValor(String chave, Object valor){
        values.put(chave, valor);
    }

    public void reset() {
        this.values.clear();
    }
}


