package com.inovaufrpe.makeparty.user.servico;

import android.content.Context;
import android.graphics.Bitmap;
import android.icu.text.DateFormat;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.DetalhesAnuncioActivity;
import com.inovaufrpe.makeparty.cliente.gui.ListaDesejosClienteActivity;
import com.inovaufrpe.makeparty.cliente.gui.TelaInicialClienteActivity;
import com.inovaufrpe.makeparty.cliente.servico.ClienteService;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;
import com.inovaufrpe.makeparty.fornecedor.dominio.Owner;
import com.inovaufrpe.makeparty.fornecedor.gui.AnunciosFornecedorActivity;
import com.inovaufrpe.makeparty.fornecedor.servico.FornecedorService;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.Response;
import com.inovaufrpe.makeparty.infra.ResponseWithURL;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.utils.FileUtils;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.utils.IOUtils;
import com.inovaufrpe.makeparty.user.dominio.Address;
import com.inovaufrpe.makeparty.user.dominio.Data;
import com.inovaufrpe.makeparty.user.dominio.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AnuncioEmComumService {

    private static final String TAG = "AnuncioEmComumService";
    private static final boolean LOG_ON = false;
    private static final String URL_BASE = "https://makepartyserver.herokuapp.com/";
    //POST, PUT, DELETE E GET
    private static final String URL_LISTAR_ANUNCIOS = URL_BASE + "ads";
    //GET ANÚNCIOS
    private static final String URL_LISTAR_ANUNCIOS_PELA_TAG = URL_LISTAR_ANUNCIOS + "/tags/:tag";
    private static final String URL_LISTAR_ANUNCIOS_PELO_TIPO = URL_LISTAR_ANUNCIOS + "/types/:type";
    private static final String URL_PESQUISAR_PJ_PELO_ID = URL_BASE + "advertisers/:id";
    private static final String URL_LISTAR_PJS = URL_BASE + "advertisers";
    private static final String URL_LISTA_ANUNCIOS_DO_ANUNCIANTE = URL_LISTAR_ANUNCIOS + "/owners/:idOuTokenDele";
    private static final String URL_LISTA_ANUNCIOS_POR_NOME = URL_LISTAR_ANUNCIOS + "titles/:title";
    private static final String URL_LISTA_ANUNCIOS_POR_ID_ANUNCIO = URL_LISTAR_ANUNCIOS + ":id";

    private Gson gson = new Gson();
    private String respostaServidor;
    private static ClienteService clienteService = new ClienteService();
    private static FornecedorService fornecedorService = new FornecedorService();


    public AnuncioEmComumService() {
    } //CONSTRUTOR

    public static String conectarServidorGet(String url) throws IOException {
        // Request HTTP GET
        ConectarServidor http = new ConectarServidor();
        http.LOG_ON = true;
        String json = http.doGet(url);
        return json;
    }

    public static String conectarServidorPost(String url, String jsonAserEnviado) {
        ConectarServidor http = new ConectarServidor();
        http.LOG_ON = true;
        String jsonRespostaAoPost = http.post(url, jsonAserEnviado);
        Log.i("Script", "OLHA resposta do servidor se foi conc listinhaa: " + jsonRespostaAoPost);
        return jsonRespostaAoPost;
    }

    /* public static String conectarServidorDelete(String url, String jsonAserEnviado){
         ConectarServidor http = new ConectarServidor();
         http.LOG_ON = true;
         String jsonRespostaAoPost = http.do(url,jsonAserEnviado);
         Log.i("Script", "OLHA resposta do servidor se foi conc listinhaa: "+ jsonRespostaAoPost);
         return jsonRespostaAoPost;
     }*/
    /*public static String conectarServidorPut(String url, String jsonAserEnviado) {
        //Esse  metodo ta errado ainda viu
        ConectarServidor http = new ConectarServidor();
        http.LOG_ON = true;
        String jsonRespostaAoPost = http.p(url, jsonAserEnviado);
        Log.i("Script", "OLHA resposta do servidor se foi conc listinhaa: " + jsonRespostaAoPost);
        return jsonRespostaAoPost;
    }*/

    private static List<Ads> parserJSON(Context context, String json) throws IOException {
        //Informa ao GSON que vamos converter uma lista de anúncios
        Type listType = new TypeToken<ArrayList<Ads>>() {
        }.getType();
        //Faz o parser em apenas uma linha e cria a list
        List<Ads> ads = new Gson().fromJson(json, listType);

        return ads;
    }

    public static List<Ads> parserJSONListaAnunciosComFor(String json) throws IOException {
        List<Ads> ads = new ArrayList<Ads>();
        try {
            JSONObject objetoJson = new JSONObject(json);
            JSONArray jsonAnuncios = objetoJson.getJSONArray("ads");
            if(SessaoApplication.getInstance().getTelaAtual().equals(ListaDesejosClienteActivity.class)){
                jsonAnuncios=objetoJson.getJSONArray("wishlists");
                //depois pedir p edson deixar como ads msm o array la
            }

            //Lê o array de ads do Json
            //JSONArray jsonAnuncios = new JSONArray(json);
            for (int i = 0; i < jsonAnuncios.length(); i++) {
                JSONObject jsonAnuncio = jsonAnuncios.getJSONObject(i);
                Ads c = new Ads();
                //Lê as info de cada anuncio
                c.setDescription(jsonAnuncio.optString("description"));
                c.setPrice(jsonAnuncio.optDouble("price"));
                //TAGS N TA FUNCIONANDO DIREITO
                JSONArray tagsArray = jsonAnuncio.getJSONArray("tags");
                List<String> listTags = new ArrayList<String>();
                for (int e=0;i<tagsArray.length();i++){
                    listTags.add(tagsArray.getString(e));
                }
                Log.d("tagsss",listTags.toString());
                c.setTags((ArrayList) listTags);


                JSONArray fotosArrayJson = jsonAnuncio.getJSONArray("photos");
                List<String> listFotos = new ArrayList<String>();
                for (int e=0;i<fotosArrayJson.length();i++){
                    listFotos.add(fotosArrayJson.getString(e));
                }
                Log.d("fotoooosArray",listFotos.toString());
                c.setPhotos((ArrayList) listFotos);

                c.set_id(jsonAnuncio.optString("_id"));
                c.setTitle(jsonAnuncio.optString("title"));
                c.setType(jsonAnuncio.optString("type"));
                c.setPhone(jsonAnuncio.optString("phone"));

                Owner ownerAqui=new Owner();
                JSONObject objetoOwner = jsonAnuncio.getJSONObject("owner");
                ownerAqui.setSocialname(objetoOwner.getString("socialname"));
                ownerAqui.setCnpj(objetoOwner.getString("cnpj"));
                ownerAqui.set_id(objetoOwner.getString("_id"));

                User userOwner = new User();
                JSONObject objetoUserOwner =objetoOwner.getJSONObject("user");
                userOwner.setEmail(objetoUserOwner.getString("email"));
                userOwner.set_id(objetoUserOwner.getString("_id"));
                ownerAqui.setUser(userOwner);
                c.setOwner(ownerAqui);
                Log.d("oi",c.toString());

                //ta errado aq embaixo
               /* String dateStr = jsonAnuncio.getString("createdAt");
                Date sdf =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").parse(dateStr);
                long createdAtDate = sdf.parse(dateStr);
                Log.d("dataveae", String.valueOf(createdAtDate)); */
                //c.setCreatedAt(createdAtDate);
                //Log.d("dataveae",c.getCreatedAt().toString());

                //c.setUpdatedAt();
               /* Long createdAt = jsonAnuncio.optLong("createdAt");
                Date createdAtConv = new Date(createdAt);
                c.setCreatedAt(createdAtConv);
                */
                Address addressAnuncio = new Address();
                JSONObject objetoEndAnuncio = jsonAnuncio.getJSONObject("address");
                //LEMBRANDO Q ESSES OBJ N PODEM FICAR NULL EXPLIC , SE N, DA ERROO NA CONV
                addressAnuncio.setCity(objetoEndAnuncio.getString("city"));
                addressAnuncio.setNeighborhood(objetoEndAnuncio.getString("neighborhood"));
                addressAnuncio.setNumber(objetoEndAnuncio.getString("number"));
                addressAnuncio.setStreet(objetoEndAnuncio.getString("street"));
                addressAnuncio.setZipcode(objetoEndAnuncio.getString("zipcode"));
                c.setAddress(addressAnuncio);


                if (LOG_ON) {
                    Log.d(TAG, "Ads" + c.getDescription() + ">");

                }
                ads.add(c);
            }
            if (LOG_ON) {
                Log.d(TAG, ads.size() + "encontrados");
            }
        } catch (JSONException e) {
            throw new IOException(e.getMessage(), e);
        }
        return ads;

    }

    private static String getTipo(int tipo) {
        if (tipo == R.string.text_pacotes) {
            return "pacotes";
        } else if (tipo == R.string.text_casas_de_festas) {
            return "casas";
        } else if (tipo == R.string.text_buffet) {
            return "buffet";
        } else if (tipo == R.string.text_decoracao) {
            return "decoracao";
        } else if (tipo == R.string.text_animacao) {
            return "animacao";
        }
        return "";
    }

    public static List getTodosAnuncios(Context context) throws IOException {
        String url = URL_LISTAR_ANUNCIOS;
        String json = conectarServidorGet(url);
        List listaAds = parserJSON(context, json);
        return listaAds;
    }

    public static List<Ads> getAnunciosByTipoTwo(Context context, String tipo) throws IOException {
        String url = URL_LISTAR_ANUNCIOS_PELO_TIPO.replace(":type", tipo);
        String json = conectarServidorGet(url);
        List listaAnunciosPorTipo = parserJSON(context, json);
        return listaAnunciosPorTipo;
    }

    public static List<Ads> getAnunciosByTipoThree(String tipo) throws IOException {
        String url = URL_LISTAR_ANUNCIOS_PELO_TIPO.replace(":type", tipo);
        String json = conectarServidorGet(url);
        Log.d("um json ai", json);
        Data data = new Gson().fromJson(json, Data.class);
        return data;
    }

    public void verifTipoReqLista(String tipo) {
        //if verifTipoReqLista(SessaoApplication.getInstance().getTipoDeUserLogado().equals("Fornecedor"));
    }

    public static List<Ads> getAnunciosByTipo(String tipo) throws IOException {
        List ads = new ArrayList<Ads>();
        if (SessaoApplication.getInstance().getTipoDeUserLogado().equals("advertiser") | tipo.equals("advertiser")) {
            ads = FornecedorService.getAnunciosDeUmFornecedor(SessaoApplication.getInstance().getTokenUser());
        } else if (SessaoApplication.getInstance().getTipoDeUserLogado().equals("customer") && (SessaoApplication.getInstance().getTelaAtual().equals(ListaDesejosClienteActivity.class))) {
            ads = ClienteService.getUserWishList(SessaoApplication.getInstance().getTokenUser());
            //Esse metodo acima ainda esta em desenvolvimento bjss , ainda falta setar tela
        } else {
            String url = URL_LISTAR_ANUNCIOS_PELO_TIPO.replace(":type", tipo);
            String json = conectarServidorGet(url);
            Log.d("um json ai", json);
            ads = parserJSONListaAnunciosComFor(json);
        }

        return ads;
    }
    public static List<Ads> getAnunciosByPriceFilter(String intervaloInicio,String intervaloFim) throws IOException {
        List ads = new ArrayList<Ads>();
        //FAZER AQ EINNNNNNNNNNNNNNNNNNNNNNNNN
        //FILTRO PREÇOOOOOOOOOO

        return ads;
    }
    public static List<Ads> searchAllAdsByNome(String nome) throws IOException {
        String url = URL_BASE + "/nome/" + nome; // << essa url ta errada, eu n sei qual url da p pesquisar pelo nome la na API
        ConectarServidor http = new ConectarServidor();
        String json = http.doGet(url);
        List litaAnunciosPorNome = parserJSONListaAnunciosComFor(json);
        return litaAnunciosPorNome;
    }
    public static boolean addItensLista(List<Ads> selectedAds) throws IOException, JSONException {
        if ((SessaoApplication.getInstance().getTipoDeUserLogado().equals("customer") &&
                ((SessaoApplication.getInstance().getTelaAtual().equals(TelaInicialClienteActivity.class)
                        | SessaoApplication.getInstance().getTelaAtual().equals(DetalhesAnuncioActivity.class))))) {

            Boolean a = clienteService.addAWishList(selectedAds);
            //aqui embaixo esse metodo acho q n é a melhor forma de implementar, talvez o wishlist? chamando ele?
            //setar ainda qual tela ta

        } else {
            return false;
        }return true;
    }

    public static boolean deleteItensLista(List<Ads> selectedAds) throws IOException, JSONException {
        //Falta setar as telas q ele ta
        if((SessaoApplication.getInstance().getTipoDeUserLogado().equals("customer")&&(SessaoApplication.getInstance().getTelaAtual().equals(ListaDesejosClienteActivity.class)))){
            ClienteService.deleteItensListaDesejoCliente(selectedAds,SessaoApplication.getInstance().getTokenUser());
        }
        else if(SessaoApplication.getInstance().getTipoDeUserLogado().equals("advertiser")&&(SessaoApplication.getInstance().getTelaAtual().equals(AnunciosFornecedorActivity.class))){
            //ta dizendo q é do item dele neh, falta verificar se ele ta na tela meus anuncios
            FornecedorService.deleteItensListaFornecedor(selectedAds,SessaoApplication.getInstance().getTokenUser());
        }else{
            return false;
        }
        return true;
    }


    public static ResponseWithURL postFotoBase64(File file) throws IOException {
        String url = URL_BASE + "/postFotoBase64";

        Log.d(TAG, "postFotoBase64: " + url);
        Log.d(TAG, "postFotoBase64 File: " + file);

        // Converte para Base64
        byte[] bytes = IOUtils.toBytes(new FileInputStream(file));
        String base64 = Base64.encodeToString(bytes, Base64.NO_WRAP);

        //Log.d(TAG, "base64: " + base64);

        Map<String, String> params = new HashMap<String, String>();
        params.put("fileName", file.getName());
        //params.put("base64", "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");
        params.put("base64", base64);

        Log.d(TAG, ">> postFotoBase64: " + params);
        ConectarServidor http = new ConectarServidor();
        http.setContentType("application/x-www-form-urlencoded");
        http.setCharsetToEncode("UTF-8");
        String json = http.doPost(url, params, "UTF-8");
        Log.d(TAG, "<< postFotoBase64: " + json);

        ResponseWithURL response = new Gson().fromJson(json, ResponseWithURL.class);

        Log.d(TAG, "ResponseWithURL: " + response);

        return response;
    }

    public static ResponseWithURL postFotoBase64(String fileName, String base64) throws IOException {
        String url = URL_BASE + "/postFotoBase64";

        Log.d(TAG, "postFotoBase64: " + url);
        Log.d(TAG, "fileName File: " + fileName);

        Map<String, String> params = new HashMap<String, String>();
        params.put("fileName", fileName);
        params.put("base64", base64);

        Log.d(TAG, ">> postFotoBase64: " + params);
        ConectarServidor http = new ConectarServidor();
        http.setContentType("application/x-www-form-urlencoded");
        http.setCharsetToEncode("UTF-8");
        String json = http.doPost(url, params, "UTF-8");
        Log.d(TAG, "<< postFotoBase64: " + json);

        ResponseWithURL response = new Gson().fromJson(json, ResponseWithURL.class);

        Log.d(TAG, "ResponseWithURL: " + response);

        return response;
    }

    public static Response saveAnuncio(Ads ads) throws IOException {
        String url = URL_BASE;

        String jsonAnuncio = new Gson().toJson(ads);
        Log.d(TAG, ">> saveAnuncio: " + jsonAnuncio);
        ConectarServidor http = new ConectarServidor();
        http.setContentType("application/json; charset=utf-8");

        // Envia o JSON do Carro no corpo do POST
        String json = http.doPost(url, jsonAnuncio.getBytes(), "UTF-8");
        Log.d(TAG, "<< saveAnuncio: " + json);

        Response response = new Gson().fromJson(json, Response.class);

        return response;
    }

    // Faz a requisição HTTP, cria a lista de anuncios e salva o JSON em arquivo
    public static List<Ads> getAnunciosFromWebService(Context context, int tipo) throws IOException {
        String tipoString = getTipo(tipo);
        String url = URL_LISTAR_ANUNCIOS_PELO_TIPO.replace(":type", tipoString);
        Log.d(TAG, "URL: " + url);
        ConectarServidor http = new ConectarServidor();
        String json = http.doGet(url);
        List<Ads> ads = parserJSON(context, json);
        salvaArquivoNaMemoriaInterna(context, url, json);
        // Depois de buscar salva os ads
        //salvarAnuncios(context, tipo, ads);
        return ads;
    }

    private static void salvaArquivoNaMemoriaInterna(Context context, String url, String json) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File f = FileUtils.getFile(context, fileName);
        IOUtils.writeString(f, json);
        Log.d(TAG, "Arquivo salvo: " + f);

    }

    public static List<Ads> getAnunciosFromRaw(Context context, int tipo) throws IOException {
        //String json = readFile(context, tipo);
        //List<Ads> anuncios = parserJSON(context, json);

        //return anuncios;
        return null;
    }

    // Abre o arquivo salvo, se existir, e cria a lista de anuncios
    public static List<Ads> getAnunciosFromArquivo(Context context, int tipo) throws IOException {
        String tipoString = getTipo(tipo);
        String fileName = String.format("anuncios_%s.json", tipoString);
        Log.d(TAG, "Abrindo arquivo: " + fileName);
        // Lê o arquivo da memória interna
        String json = FileUtils.readFile(context, fileName, "UTF-8");
        if (json == null) {
            Log.d(TAG, "Arquivo " + fileName + " não encontrado.");
            return null;
        }
        List<Ads> ads = parserJSON(context, json);
        Log.d(TAG, "Retornando ads do arquivo " + fileName + ".");
        return ads;
    }

    //converte um objeto para json
    public String criarJson(Object objeto) {
        return gson.toJson(objeto);
    }

    //converte um json para objeto
    public Ads criarObjeto(String json) {
        return gson.fromJson(json, Ads.class);

    }

    public static ArrayList<Bitmap> getImagensAnuncioSelecionado(){
        //ISSO AQ EMBAIXO É DO HAMBA, PRECISA DE REMODELAÇÃO PQ IREMOS PEGAR DO ARRAYLIST DAS URLS DE CADA ANUNCIO
        //PEGAR DO PICASSO E PEI
        ArrayList<Bitmap> bitMapList = new ArrayList<>();
        //verificar se o arraylist das fotos de cada anúncio n ta vazia ainda, e tbm se pode mais de 2 fotos
        /*Titulo titulo = FiltroTitulo.instance.getTituloSelecionado();
        TituloDao tituloDao = new TituloDao();
        ArrayList<byte[]> imagensByte = tituloDao.getImagemByIdTitulo(titulo.getId());
        FormatadorImagemSimples formatadorImagemSimples = new FormatadorImagemSimples();
        bitMapList = formatadorImagemSimples.listByteToListBitmap(imagensByte);
        */
       /* if(tavazioalistadeUrl){
            //mandar o array com uma só foto , ou no caso 2, pq to testando a adaptação aq
        }
        */
        return bitMapList;
    }
}
