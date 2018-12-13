package com.inovaufrpe.makeparty.cliente.servico;

import android.util.Log;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.cliente.dominio.Customer;
import com.inovaufrpe.makeparty.cliente.dominio.WishListService;
import com.inovaufrpe.makeparty.cliente.dominio.Wishlists;
import com.inovaufrpe.makeparty.cliente.gui.ListaDesejosClienteActivity;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.fornecedor.dominio.Owner;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.Response;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.dominio.Address;
import com.inovaufrpe.makeparty.user.dominio.User;
import com.inovaufrpe.makeparty.user.servico.AnuncioEmComumService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.inovaufrpe.makeparty.user.servico.AnuncioEmComumService.conectarServidorGet;

public class ClienteService{

    private static final String TAG = "ClienteService";
    private static final boolean LOG_ON = false;
    private static final String URL_BASE = "https://makepartyserver.herokuapp.com/";
    private static final String URL_CADASTRAR_PF = URL_BASE + "users/signup/customer";
    private static final String URL_ATUALIZAR_PF = URL_BASE + "customers";
    private static final String URL_ATUALIZAR_TOKEN = URL_BASE + "users/refresh-token";
    private static final String URL_AUTENTICAR_USUARIO = URL_BASE + "/users/authenticate";
    private static final String URL_PESQUISAR_PF_PELO_ID = URL_BASE + "customers/:id";
    private static final String URL_PESQUISAR_PF_PELO_TOKEN = URL_BASE + "customers/token?token=:tokenAqui";
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
    public String pegarDadosDoCliente(String tokenOuId) throws IOException{
        String url = URL_PESQUISAR_PF_PELO_TOKEN.replace(":tokenAqui",tokenOuId);
        String json =conectarServidorGet(url);
        ///Converte String JSON para objeto Java
        Gson gson = new Gson();
        Customer customer = gson.fromJson(json,Customer.class);
        SessaoApplication.getInstance().setObjCustomerSeEleForTipoLogado(customer);
        return json;
    }

    private static List<Ad> parserJSONListaAnunciosWishComFor(String json) throws IOException {
        List<Ad> ads = new ArrayList<Ad>();
        try {
            JSONObject objetoJson = new JSONObject(json);
            JSONArray jsonAnuncios = null;
            if(SessaoApplication.getInstance().getTelaAtual().equals(ListaDesejosClienteActivity.class)){
                jsonAnuncios=objetoJson.getJSONArray("wishlists");
            }else{
                jsonAnuncios = objetoJson.getJSONArray("ads");
            }

            //Lê o array de ads do Json
            //JSONArray jsonAnuncios = new JSONArray(json);
            for (int i = 0; i < jsonAnuncios.length(); i++) {
                JSONObject jsonAnuncio = jsonAnuncios.getJSONObject(i);
                Wishlists wish = new Wishlists();
                Ad adWi = new Ad();
                JSONObject objetoAd = jsonAnuncio.getJSONObject("ad");
                adWi.setDescription(jsonAnuncio.optString("description"));
                adWi.setPrice(jsonAnuncio.optDouble("price"));
                //Lê as info de cada anuncio

                //TAGS N TA FUNCIONANDO DIREITO

                JSONArray tagsArray = objetoAd.getJSONArray("tags");
                List<String> listTags = new ArrayList<String>();
                for (int e=0;i<tagsArray.length();i++){
                    listTags.add(tagsArray.getString(e));
                }
                Log.d("tagsss",listTags.toString());
                adWi.setTags((ArrayList) listTags);


                JSONArray fotosArrayJson = objetoAd.getJSONArray("photos");
                List<String> listFotos = new ArrayList<String>();
                for (int e=0;i<fotosArrayJson.length();i++){
                    listFotos.add(fotosArrayJson.getString(e));
                }
                Log.d("fotoooosArray",listFotos.toString());
                adWi.setPhotos((ArrayList) listFotos);


                adWi.set_id(objetoAd.optString("_id"));
                adWi.setTitle(objetoAd.optString("title"));
                adWi.setType(objetoAd.optString("type"));
                adWi.setPhone(objetoAd.optString("phone"));

                Owner ownerAqui=new Owner();
                JSONObject objetoOwnerDentro = objetoAd.getJSONObject("owner");
                ownerAqui.setSocialname(objetoOwnerDentro.getString("socialname"));
                ownerAqui.setCnpj(objetoOwnerDentro.getString("cnpj"));
                ownerAqui.set_id(objetoOwnerDentro.getString("_id"));

                User userOwner = new User();
                JSONObject objetoUserOwner =objetoOwnerDentro.getJSONObject("user");
                userOwner.setEmail(objetoUserOwner.getString("email"));
                userOwner.set_id(objetoUserOwner.getString("_id"));
                ownerAqui.setUser(userOwner);
                adWi.setOwner(ownerAqui);
                Log.d("oi",adWi.toString());

                //ta errado aq embaixo
               /* String dateStr = objetoAd.getString("createdAt");
                Date sdf =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(dateStr);
                long createdAtDate = sdf.parse(dateStr);
                Log.d("dataveae", String.valueOf(createdAtDate));
                //c.setCreatedAt(createdAtDate);
                //Log.d("dataveae",c.getCreatedAt().toString());

                //c.setUpdatedAt();
                Long createdAt = objetoAd.optLong("createdAt");
                Date createdAtConv = new Date(createdAt);
                adWi.setCreatedAt(createdAtConv);
                */


                Address addressAnuncio = new Address();
                JSONObject objetoEndAnuncio = objetoAd.getJSONObject("address");
                //LEMBRANDO Q ESSES OBJ N PODEM FICAR NULL EXPLIC , SE N, DA ERROO NA CONV
                addressAnuncio.setCity(objetoEndAnuncio.getString("city"));
                addressAnuncio.setNeighborhood(objetoEndAnuncio.getString("neighborhood"));
                addressAnuncio.setNumber(objetoEndAnuncio.getString("number"));
                addressAnuncio.setStreet(objetoEndAnuncio.getString("street"));
                addressAnuncio.setZipcode(objetoEndAnuncio.getString("zipcode"));
                adWi.setAddress(addressAnuncio);


                if (LOG_ON) {
                    Log.d(TAG, "Ad" + adWi.getDescription() + ">");

                }
                ads.add(adWi);
            }
            if (LOG_ON) {
                Log.d(TAG, ads.size() + "encontrados");
            }
        } catch (JSONException e) {
            throw new IOException(e.getMessage(), e);
       /* } catch (ParseException e) {
            e.printStackTrace();*/
        }
        return ads;

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

    public static boolean addAWishList(List<Ad> listAnunciosSelecPAddWishList) throws IOException {
        String url = URL_CRIAR_LISTA_DESEJOS;
        //de um por um( id do anuncio + token)
        String token=  "," + "\"token\"" + ":" + "\""+SessaoApplication.getInstance().getTokenUser() +"\"" +"}";
        for (Ad c: listAnunciosSelecPAddWishList){
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
        List listaDesejosCliente = parserJSONListaAnunciosWishComFor(json);
        return listaDesejosCliente;

    }
    public static boolean deleteItensListaDesejoCliente(List<Ad> selectedAds, String tokenOuId) throws IOException, JSONException {
        String url = URL_LISTA_DESEJOS;
        //de um por um( id do anuncio + token)
        String token = "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
        for (Ad c : selectedAds) {
            String jsonAMao = "{" + "\"ad\":" + "\"" + c.get_id() + "\"" + token;
            Log.d(TAG, "JSON a mao: " + jsonAMao);
            String respostaServidorAoExcluir = ConectarServidor.deleteDeJadiel(url, jsonAMao);///NAOOOOOO É POST, É DELETEEEE
            Log.d(TAG, "Resposta servidor ao excluir da lista: " + respostaServidorAoExcluir);
            Gson gson = new Gson();
            /*Response response = gson.fromJson(respostaServidorAoExcluir, Response.class);
            if (!response.isOk()) {
                throw new IOException("Erro ao excluir da lista " + response.getMsg());
            }*/
        }
        return true;
    }
    public void getUserWishListComRetrofit(String tokenOuId) throws IOException {
        String url = URL_LISTA_DESEJOS.replace(":tokenAqui", tokenOuId);
        //String json =conectarServidorGet(url);
        //Log.d("um json ai", json);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WishListService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WishListService service = retrofit.create(WishListService.class);
        Call<Wishlists> requestWishlists = service.wishListUser();
        requestWishlists.enqueue(new Callback<Wishlists>() {
            @Override
            public void onResponse(Call<Wishlists> call, retrofit2.Response<Wishlists> response) {
                if (!response.isSuccessful()) {
                    Log.i("RETROFITCODSUF", "Erro:" + response.code());
                } else {
                    //Requisicao retornou com sucesso
                    Wishlists wishlists = response.body();
                    for (Ad df : wishlists.ads) {
                        Log.i("jjRetrofit", String.format("%s:%s", df.get_id(), df.getPrice()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Wishlists> call, Throwable t) {
                Log.i("opaRetrofit", t.getMessage());
            }
        });


    }
       // return listaDesejosCliente;


}
