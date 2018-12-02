package com.inovaufrpe.makeparty.fornecedor.servico;

import android.util.Log;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;
import com.inovaufrpe.makeparty.fornecedor.dominio.Owner;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.Response;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.usuario.servico.AnuncioEmComumService;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.inovaufrpe.makeparty.usuario.servico.AnuncioEmComumService.conectarServidorGet;
import static com.inovaufrpe.makeparty.usuario.servico.AnuncioEmComumService.getAnunciosByTipo;

public class FornecedorService {

    private static final String TAG = "FornecedorService";
    private static final boolean LOG_ON = false;
    private static final String URL_BASE = "https://makepartyserver.herokuapp.com/";
    private static final String URL_CADASTRO_PJ = URL_BASE + "users/signup/advertiser";
    private static final String URL_ATUALIZAR_PJ = URL_BASE + "advertisers";
    private static final String URL_ATUALIZAR_TOKEN = URL_BASE + "users/refresh-token";
    private static final String URL_AUTENTICAR_USUARIO = URL_BASE + "/users/authenticate";
    private static final String URL_PESQUISAR_PJ_PELO_ID = URL_BASE + "advertisers/:id";
    private static final String URL_LISTAR_PJS = URL_BASE + "advertisers";
    private static final String URL_LISTAR_USUARIOS = URL_BASE +"users";
    //POST, PUT, DELETE E GET
    private static final String URL_COLOCAR_ANUNCIO = URL_BASE + "ads";
    private static final String URL_LISTAR_ANUNCIOS = URL_BASE + "ads";
    private static final String URL_LISTAR_MEUS_ANUNCIOS = URL_LISTAR_ANUNCIOS + "/owners?token=:tokenAqui";
    private static final String URL_LISTA_ANUNCIOS_DO_ANUNCIANTE = URL_LISTAR_MEUS_ANUNCIOS+ "/owners/:idDele";
    private static final String URL_APAGAR_ANUNCIO = URL_LISTAR_ANUNCIOS;

    private AnuncioEmComumService anuncioEmComumService = new AnuncioEmComumService();
    private Gson gson = new Gson();
    private String respostaServidor;


    public FornecedorService() {
    } //CONSTRUTOR

    //converte um objeto para json
    public String criarJson(Object objeto) {
        return gson.toJson(objeto);
    }

    //converte um json para objeto
    public Owner criarObjeto(String json) {
        return gson.fromJson(json,Owner.class);
    }

    //método que usa a requisição http implementada em conexaoServidor para criar usuário
    public void criarFornecedor(Object objeto) throws IOException {
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

    public static String getTAG() {
        return TAG;
    }

    public static boolean isLogOn() {
        return LOG_ON;
    }

    public static String getUrlBase() {
        return URL_BASE;
    }

    public void criarAnuncio(Object objeto) throws IOException {
        String novoJson = criarJson(objeto);
    }

    public static List getAnunciosDeUmFornecedor(String tokenOuId) throws IOException {
        String url = URL_LISTAR_MEUS_ANUNCIOS.replace(":tokenAqui",tokenOuId);
        String json =conectarServidorGet(url);
        Log.d("um json ai", json);
        List listaAnunciosFornecedor = AnuncioEmComumService.parserJSONListaAnunciosComFor(json);
        return listaAnunciosFornecedor;
    }
    public static boolean deleteItensListaFornecedor(List<Ads> selectedAds) throws IOException, JSONException {
        ConectarServidor http = new ConectarServidor();
        http.setContentType("application/json; charset=utf-8");
        for (Ads c : selectedAds) {
            // URL para excluir o anúncio
            //verificar como faz a exclusão do anúncio, n entendi se era pela url ou mandando msg p ele só
            //Refazer metodo e tbm se é p apagar de um em um, ou mando a lista td
            //mandar tbm token
            String url = URL_APAGAR_ANUNCIO + c._id;
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
