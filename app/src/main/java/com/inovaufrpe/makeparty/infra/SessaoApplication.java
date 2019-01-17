package com.inovaufrpe.makeparty.infra;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.inovaufrpe.makeparty.cliente.dominio.Customer;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.fornecedor.dominio.Owner;
import com.inovaufrpe.makeparty.user.dominio.User;
import com.squareup.otto.Bus;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessaoApplication extends Application {

    private static final String TAG = " SessaoApplication";
    public static SessaoApplication instance = null;
    private final Map<String, Object> values = new HashMap<>();
    private Bus bus = new Bus();
    private String tipoDeUserLogado = "null";
    private String token = "null";
    private String _id = "null";
    private User user;
    private Date horaRecebidoToken = null;
    private Owner ownerIsLogado = null;
    private Customer customerIsLogado =null;
    private Class telaAtual =null;
    private Class telaAnterior =null;
    private static Context mContext;
    private static List<Ad> adRecomendacao;

    public static SessaoApplication getInstance() {
        return instance; // Singleton
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, " SessaoApplication.onCreate()");
        // Salva a instância para termos acesso como Singleton
        instance = this;
        mContext = this;
    }

    private void setValor(String chave, Object valor) {
        values.put(chave, valor);
    }

    public void setResposta(String resposta) {
        setValor("sessao.resposta", resposta);
    }

    public String getResposta(){
        return (String) values.get("sessao.resposta");
    }

    public String getTipoDeUserLogado(){
        return tipoDeUserLogado;
    }
    public void setTipoDeUserLogado(String tipoDeUserLogado) {
        this.tipoDeUserLogado = tipoDeUserLogado;
    }


    public String getIdUser() {
        return _id;
    }

    public void setIdUser(String _id) {
        this._id = _id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTokenUser() {
        return token;
    }

    public void setTokenUser(String token) {
        this.token = token;
    }

    public void setHoraRecebidoToken(Date horaRecebidoToken){
        this.horaRecebidoToken = horaRecebidoToken;
    }
    public Date getHoraRecebidoToken() {
        return horaRecebidoToken;
    }
    public Owner getObjOwnerSeEleForTipoLogado(){
        return ownerIsLogado;
    }
    public void setObjOwnerSeEleForTipoLogado(Owner owner){
        ownerIsLogado=owner;
    }
    public Customer getObjCustomerSeEleForTipoLogado(){
        return customerIsLogado;
    }
    public void setObjCustomerSeEleForTipoLogado(Customer customer){
        customerIsLogado=customer;
    }

    public void setTelaAtual(Class telaAtual) {
        setTelaAnterior(this.telaAtual);
        this.telaAtual =telaAtual;
    }
    public Class getTelaAtual() {
        return telaAtual;
    }
    public void setTelaAnterior(Class telaAnterior){
        this.telaAnterior = telaAnterior;
    }
    public Class getTelaAnterior(){
        return telaAnterior;
    }
    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onTerminate() {
        reset();
        super.onTerminate();
        SessaoApplication.getInstance().setTipoDeUserLogado("null");
        SessaoApplication.getInstance().setTokenUser("null");
        SessaoApplication.getInstance().setIdUser("null");
        SessaoApplication.getInstance().setHoraRecebidoToken(null);
        SessaoApplication.getInstance().setTelaAnterior(null);
        SessaoApplication.getInstance().setObjOwnerSeEleForTipoLogado(null);
        SessaoApplication.getInstance().setObjCustomerSeEleForTipoLogado(null);
        Log.d(TAG, " SessaoApplication.onTerminate()");
    }

    public Bus getBus() {
        return bus;
    }

    public void reset() {
        this.values.clear();
    }

    public static List<Ad> getAdRecomendacao() {
        return adRecomendacao;
    }

    public static void setAdRecomendacao(List<Ad> adRecomendacao) {
        SessaoApplication.adRecomendacao = adRecomendacao;
    }
}
