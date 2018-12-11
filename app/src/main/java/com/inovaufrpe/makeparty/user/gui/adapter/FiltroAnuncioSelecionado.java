package com.inovaufrpe.makeparty.user.gui.adapter;

import com.inovaufrpe.makeparty.cliente.gui.TelaInicialClienteActivity;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.infra.SessaoApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FiltroAnuncioSelecionado {
    public static final FiltroAnuncioSelecionado instance = new FiltroAnuncioSelecionado();
    private final Map<String, Object> values = new HashMap<>();
    private String tipoAnuncioBottomNavSoPCliente ="null";

    public String getTipoListaPraMostrarSubCategoriaBottomNavCliente(){
        if ((tipoAnuncioBottomNavSoPCliente.equals("null")|(!SessaoApplication.getInstance().getTelaAtual().equals(TelaInicialClienteActivity.class)))){
            //tem q definir aq como o primeiro tipo q aparece no bottom navig
            this.tipoAnuncioBottomNavSoPCliente="festa";
        }
        return tipoAnuncioBottomNavSoPCliente;
    }
    public void setTipoListaPraMostrarSubCategoriaBottomNavCliente(String tipoSubCategoriaBottomNav){
        this.tipoAnuncioBottomNavSoPCliente= tipoSubCategoriaBottomNav;
    }
    public void setAnunciosList(ArrayList<Ad> anunciosList) {
        setValor("FiltroAnuncioSelecionado.anunciosList", anunciosList);
    }

    public ArrayList<Ad> getAnunciosList() {
        return (ArrayList<Ad>) values.get("FiltroAnuncioSelecionado.anunciosList");
    }

    public Ad getAnuncioSelecionado() {
        return (Ad) values.get("sessao.AnuncioSelecionado");
    }

    public void setAnuncioSelecionado(Ad anuncioSelecionado) {
        setValor("sessao.AnuncioSelecionado", anuncioSelecionado);
    }

    public Ad getTipoAnuncioSelecionado() {
        return (Ad) values.get("sessao.AnuncioSelecionado");
    }

    public void setTipoAnuncioSelecionado(Ad anuncioSelecionado) {
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


