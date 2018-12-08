package com.inovaufrpe.makeparty.cliente.servico;

import android.util.Log;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.cliente.dominio.Customer;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.Response;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.usuario.servico.AnuncioEmComumService;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.inovaufrpe.makeparty.usuario.servico.AnuncioEmComumService.conectarServidorGet;

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
    private static final String URL_LISTA_DESEJOS = URL_BASE + "wishlists"+"?token=:tokenAqui";
    private static final String URL_LISTAR_ANUNCIOS_POR_PRECO = URL_BASE + "ads/prices/price";
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
    public Customer criarObjeto(String json) {
        return gson.fromJson(json, Customer.class);
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

    public static boolean addAWishList(List<Ads> listAnunciosSelecPAddWishList) throws IOException {
        String url = URL_CRIAR_LISTA_DESEJOS;
        //de um por um( id do anuncio + token)
        String token=  "," + "\"token\"" + ":" + "\""+SessaoApplication.getInstance().getTokenUser() +"\"" +"}";
        for (Ads c: listAnunciosSelecPAddWishList){
            String jsonAMao ="{" + "\"ad\":"+"\""+c.get_id()+"\""+ token;
            Log.d(TAG, "JSON a mao: " + jsonAMao);
            String respostaServidorAoAdd= ConectarServidor.post(url,jsonAMao);
            Log.d(TAG, "JSON a mao: " + jsonAMao);
            Log.d(TAG, "Resposta servidor a add da lista: " + respostaServidorAoAdd);
            Gson gson = new Gson();
            Response response = gson.fromJson(respostaServidorAoAdd, Response.class);
            if (!response.isOk()) {
                throw new IOException("Erro ao adicionar a lista de desejo " + response.getMsg());
            }
        }return true;

    }
    public static List getUserWishList(String tokenOuId) throws IOException{
        String url = URL_LISTA_DESEJOS.replace(":tokenAqui",tokenOuId);
        String json =conectarServidorGet(url);
        Log.d("um json ai", json);
        List listaDesejosCliente = AnuncioEmComumService.parserJSONListaAnunciosComFor(json);
        return listaDesejosCliente;

    }
    public static boolean deleteItensListaDesejoCliente(List<Ads> selectedAds,String tokenOuId) throws IOException, JSONException {
        String url = URL_LISTA_DESEJOS;
        //de um por um( id do anuncio + token)
        String token = "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
        for (Ads c : selectedAds) {
            String jsonAMao = "{" + "\"ad\":" + "\"" + c.get_id() + "\"" + token;
            Log.d(TAG, "JSON a mao: " + jsonAMao);
            String respostaServidorAoExcluir = ConectarServidor.deleteDeJadiel(url, jsonAMao);///NAOOOOOO É POST, É DELETEEEE
            Log.d(TAG, "Resposta servidor ao excluir da lista: " + respostaServidorAoExcluir);
            Gson gson = new Gson();
            Response response = gson.fromJson(respostaServidorAoExcluir, Response.class);
            if (!response.isOk()) {
                throw new IOException("Erro ao excluir da lista " + response.getMsg());
            }
        }
        return true;
    }


}
