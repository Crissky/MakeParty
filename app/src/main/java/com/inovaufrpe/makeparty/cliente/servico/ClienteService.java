package com.inovaufrpe.makeparty.cliente.servico;

import android.util.Log;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.cliente.dominio.PessoaFisica;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.Response;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.usuario.servico.AnuncioEmComumService;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.inovaufrpe.makeparty.usuario.servico.AnuncioEmComumService.conectarServidorPost;

public class ClienteService{

    private static final String TAG = "ClienteService";
    private static final boolean LOG_ON = false;
    private static final String URL_BASE = "https://makepartyserver.herokuapp.com/";
    private static final String URL_CADASTRAR_PF = URL_BASE + "users/signup/customer";
    private static final String URL_ATUALIZAR_PF = URL_BASE + "customers";
    private static final String URL_ATUALIZAR_TOKEN = URL_BASE + "users/refresh-token";
    private static final String URL_AUTENTICAR_USUARIO = URL_BASE + "/users/authenticate";
    private static final String URL_PESQUISAR_PF_PELO_ID = URL_BASE + "customers/:id";
    private static final String URL_LISTAR_USUARIOS = URL_BASE + "users";
    private static final String URL_CRIAR_LISTA_DESEJOS = URL_BASE + "wishlists";
    private static final String URL_LISTA_DESEJOS = URL_BASE + "wishlists";
    private AnuncioEmComumService anuncioEmComumService = new AnuncioEmComumService();
    private Gson gson = new Gson();
    private String respostaServidor;

    public ClienteService() {
    } //CONSTRUTOR

    //converte um objeto para json
    public String criarJson(Object objeto) {
        return gson.toJson(objeto);
    }

    //converte um json para objeto
    public PessoaFisica criarObjeto(String json) {
        return gson.fromJson(json, PessoaFisica.class);
    }


    public void criarCliente(Object objeto) throws IOException {
        String novoJson = criarJson(objeto);

    }


    //Metodo que quebra o json e pega a 2 }== token que é preciso para identificar o user nas requisicoes para n ter q ficar pedindo direto o id/token que o identifica
    // eu uso esse metodo , pegando o token e salvando no SharedPreferens
    public String pegandoTokenNoJson(String json) {
        Map<String, Object> jsonNodes = gson.fromJson(json, Map.class);
        String resultado = jsonNodes.get("token").toString();
        return resultado;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }


    public String getRespostaServidor() {
        return respostaServidor;
    }

    public void setRespostaServidor(String respostaServidor) {
        this.respostaServidor = respostaServidor;
    }

    public static boolean isLogOn() {
        return LOG_ON;
    }

    public static String getUrlBase() {
        return URL_BASE;
    }

    public String addWishList(List listAnuncios) {
        String url = URL_CRIAR_LISTA_DESEJOS;
        //transf lista em json
        Gson gson = new Gson();
        String listString = gson.toJson(listAnuncios);
        //colocando token
        listString = listString.substring(0, listString.length() - 1) + "," + "\"token\"" + ":" + SessaoApplication.getInstance().getTokenUser() + "}";
        Log.i("Script", "OLHA listinhaa: " + listString);
        String respostaAoPost = conectarServidorPost(url, listString);
        return respostaAoPost;
    }

    public void excluirAnuncioDaWishList(List listAnunciosExcluidos){

    }
    public void getUserWishList(){

    }
    public static boolean deleteItensListaCliente(List<Ads> selectedAds) throws IOException, JSONException {
        ConectarServidor http = new ConectarServidor();
        http.setContentType("application/json; charset=utf-8");
        for (Ads c : selectedAds) {
            // URL para excluir o anúncio
            String url = URL_LISTA_DESEJOS + c._id;
            Log.d(TAG, "Delete anuncio: " + url);
            // Request HTTP DELETE
            String json = http.doDelete(url);
            Log.d(TAG, "JSON delete: " + json);
            // Parser do JSON
            Gson gson = new Gson();
            Response response = gson.fromJson(json, Response.class);
            if (!response.isOk()) {
                throw new IOException("Erro ao excluir: " + response.getMsg());
            }
        }
        // A fazer
        return true;
    }


}
