package com.inovaufrpe.makeparty.fornecedor.gui.adapter;

import com.inovaufrpe.makeparty.fornecedor.dominio.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FiltroEventoSelecionado {
    public static final FiltroEventoSelecionado instance = new FiltroEventoSelecionado();
    private final Map<String, Object> values = new HashMap<>();

    public void setEventsList(ArrayList<Event> eventsList) {
        setValor("FiltroAnuncioSelecionado.anunciosList", eventsList);
    }

    public ArrayList<Event> getEventsList() {
        return (ArrayList<Event>) values.get("FiltroEventoSelecionado.eventsList");
    }

    public Event getEventoSelecionado() {
        return (Event) values.get("sessao.EventSelecionado");
    }

    public void setEventoSelecionado(Event eventoSelecionado) {
        setValor("sessao.EventSelecionado", eventoSelecionado);
    }

    public Event getTipoEventoSelecionado() {
        return (Event) values.get("sessao.EventoSelecionado");
    }

    public void setTipoEventoSelecionado(Event eventoSelecionado) {
        setValor("sessao.EventoSelecionado", eventoSelecionado);
    }
    public String getDiaSelecionadoPeloFornDisp(){
        return ((String) values.get("sessao.DiaSelecionadoPeloForn"));
    }
    public void setDiaSelecionadoPeloFornDisp(String dataSelecionadaPeloFornDisp){
        setValor("sessao.DiaSelecionadoPeloFornDisp", dataSelecionadaPeloFornDisp);

    }
    private void setValor(String chave, Object valor){
        values.put(chave, valor);
    }

    public void reset() {
        this.values.clear();
    }
}
