package com.inovaufrpe.makeparty.fornecedor.servico;

import android.util.Log;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.fornecedor.dominio.Event;
import com.inovaufrpe.makeparty.fornecedor.dominio.Owner;
import com.inovaufrpe.makeparty.fornecedor.dominio.Plano;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.Response;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.dominio.Address;
import com.inovaufrpe.makeparty.user.servico.AnuncioEmComumService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.inovaufrpe.makeparty.user.servico.AnuncioEmComumService.conectarServidorGet;

public class FornecedorService {

    private static final String TAG = "FornecedorService";
    private static final boolean LOG_ON = false;
    private static final String URL_BASE = "https://makepartyserver.herokuapp.com/";
    private static final String URL_CADASTRO_PJ = URL_BASE + "users/signup/advertiser";
    private static final String URL_ATUALIZAR_PJ = URL_BASE + "advertisers";
    private static final String URL_ATUALIZAR_TOKEN = URL_BASE + "users/refresh-token";
    private static final String URL_AUTENTICAR_USUARIO = URL_BASE + "/users/authenticate";
    private static final String URL_PESQUISAR_PJ_PELO_ID = URL_BASE + "advertisers/:id";
    private static final String URL_PESQUISAR_PJ_PELO_TOKEN = URL_BASE + "advertisers/token?token=:tokenAqui";
    private static final String URL_LISTAR_PJS = URL_BASE + "advertisers";
    private static final String URL_LISTAR_USUARIOS = URL_BASE +"users";
    //POST, PUT, DELETE E GET
    private static final String URL_COLOCAR_ANUNCIO = URL_BASE + "ads";
    private static final String URL_LISTAR_ANUNCIOS = URL_BASE + "ads";
    private static final String URL_LISTAR_MEUS_ANUNCIOS = URL_LISTAR_ANUNCIOS + "/owners?token=:tokenAqui";
    private static final String URL_LISTA_ANUNCIOS_DO_ANUNCIANTE = URL_LISTAR_MEUS_ANUNCIOS+ "/owners/:idDele";
    private static final String URL_LISTAR_EVENTOS = URL_BASE + "events"; //acho q non...pq evento é restrito
    private static final String URL_LISTAR_MEUS_EVENTOS = URL_LISTAR_EVENTOS + "/?token=:tokenAqui";
    private static final String URL_DELETE_MEUS_EVENTOS = URL_LISTAR_EVENTOS + "/advertisers?token=:tokenAqui";
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
    public String pegarDadosDoFornecedor(String tokenOuId) throws IOException{
        String url = URL_PESQUISAR_PJ_PELO_TOKEN.replace(":tokenAqui",tokenOuId);
        String json =conectarServidorGet(url);
        ///Converte String JSON para objeto Java
        Gson gson = new Gson();
        Owner owner = gson.fromJson(json,Owner.class);
        SessaoApplication.getInstance().setObjOwnerSeEleForTipoLogado(owner);
        return json;
    }

    public static List getAnunciosDeUmFornecedor(String tokenOuId) throws IOException {
        String url = URL_LISTAR_MEUS_ANUNCIOS.replace(":tokenAqui",tokenOuId);
        String json =conectarServidorGet(url);
        Log.d("um json ai", json);
        List listaAnunciosFornecedor = AnuncioEmComumService.parserJSONListaAnunciosComFor(json);
        return listaAnunciosFornecedor;
    }
    public static boolean deleteItensListaAnunciosFornecedor(List<Ad> selectedAds, String tokenOuId) throws IOException, JSONException {
        String url = URL_APAGAR_ANUNCIO;
        //de um por um( id do anuncio + token)
        Log.d("ListaDeleteForne",selectedAds.toString());
        String token = "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
        for(int i = 0 ; i < selectedAds.size(); i++){
            String jsonAMao = "{" + "\"_id\":" + "\"" + selectedAds.get(i).get_id() + "\"" + token;
            Log.d(TAG, "JSON a mao: " + jsonAMao);
            String respostaServidorAoExcluir = ConectarServidor.deleteDeJadiel(url, jsonAMao);///NAOOOOOO É POST, É DELETEEEE
            Log.d(TAG, "Resposta servidor ao excluir da lista: " + respostaServidorAoExcluir);
            Gson gson = new Gson();
            Response response = gson.fromJson(respostaServidorAoExcluir, Response.class);
            if (!response.isOk()) {
                throw new IOException("Erro ao excluir da lista " + response.getMsg());
            }
        }
        atualizarOwner(selectedAds.size());
        return true;
    }
    private static List<Event> parserJSONListaEventsComFor(String json) throws IOException {
        List<Event> events = new ArrayList<Event>();
        try {
            JSONObject objetoJson = new JSONObject(json);
            //Lê o array de events do json
            JSONArray jsonEvents = objetoJson.getJSONArray("events");

            //Lê o array de ads do Json
            //JSONArray jsonAnuncios = new JSONArray(json);
            for (int i = 0; i < jsonEvents.length(); i++) {
                JSONObject jsonEvent = jsonEvents.getJSONObject(i);
                Event eventWe = new Event();
                //Lê as info de cada evento

                //LEMBRANDO Q ESSES OBJ N PODEM FICAR NULL EXPLIC , SE N, DA ERROO NA CONV
                Address endAqui = new Address();
                JSONObject objetoEndEv = jsonEvent.getJSONObject("address");
                endAqui.setCity(objetoEndEv.getString("city"));
                endAqui.setNeighborhood(objetoEndEv.getString("neighborhood"));
                endAqui.setNumber(objetoEndEv.getString("number"));
                endAqui.setStreet(objetoEndEv.getString("street"));
                endAqui.setZipcode(objetoEndEv.getString("zipcode"));
                eventWe.setAddress(endAqui);

                eventWe.setDescription(jsonEvent.optString("description"));
                eventWe.setClient(jsonEvent.optString("client"));
                eventWe.setStartdate(jsonEvent.optString("startdate"));
                eventWe.setEnddate(jsonEvent.optString("enddate"));
                eventWe.setType(jsonEvent.optString("type"));


                if (LOG_ON) {
                    Log.d(TAG, "Event" + eventWe.getDescription() + ">");

                }
                events.add(eventWe);
            }
            if (LOG_ON) {
                Log.d(TAG, events.size() + "encontrados");
            }
        } catch (JSONException e) {
            throw new IOException(e.getMessage(), e);
       /* } catch (ParseException e) {
            e.printStackTrace();*/
        }
        return events;

    }

    public static List getEventosDeUmFornecedor(String tokenOuId) throws IOException {
        String url = URL_LISTAR_MEUS_EVENTOS.replace(":tokenAqui",tokenOuId);
        String json =conectarServidorGet(url);
        Log.d("um json ai", json);
        List listaAnunciosFornecedor =FornecedorService.parserJSONListaEventsComFor(json);
        return listaAnunciosFornecedor;
    }
    public static boolean deleteItensListaEventosFornecedor(List<Event> selectedEvents, String tokenOuId) throws IOException, JSONException {
        String url = URL_DELETE_MEUS_EVENTOS;
        //de um por um( id do anuncio + token)
        Log.d("ListaDeleteForne",selectedEvents.toString());
        String token = "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
        for(int i = 0 ; i < selectedEvents.size(); i++){
            String jsonAMao = "{" + "\"_id\":" + "\"" + selectedEvents.get(i).get_id() + "\"" + token;
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

    private static void atualizarOwner(int numeroAds){
        Owner owner = SessaoApplication.getInstance().getObjOwnerSeEleForTipoLogado();
        Plano plano = owner.getPlan();
        plano.setTotalad(plano.getTotalad() - numeroAds);
//        plano.setTotalphoto(plano.getTotalphoto() - bitmaps.size());
        Gson gson = new Gson();
        String newOwner = gson.toJson(owner);
        final String ownerSend = newOwner.substring(0, newOwner.length() - 1) + "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
        try {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String validarPlano = ConectarServidor.putJadiel("https://makepartyserver.herokuapp.com/advertisers", ownerSend);
                    Log.i("Script", "OLHAAA: " + validarPlano);
                    if (validarPlano.substring(2, 5).equals("err")) {
                        validarPlano = "Não foi possível editar o perfil";
                    } else {
                        validarPlano = "Perfil editado com sucesso";
                    }
                }
            });
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
