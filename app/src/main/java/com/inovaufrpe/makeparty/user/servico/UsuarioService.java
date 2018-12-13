package com.inovaufrpe.makeparty.user.servico;

import android.util.Log;

import com.inovaufrpe.makeparty.cliente.servico.ClienteService;
import com.inovaufrpe.makeparty.fornecedor.servico.FornecedorService;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.SessaoApplication;

import java.io.IOException;

public class UsuarioService {

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
    public String verificarTipoUserPegarDadosDeleEGuardarNaSessao(String tokenAqui)throws IOException{
        String retorno = "nada";
        if (SessaoApplication.getInstance().getTipoDeUserLogado().equals("advertiser")){
            FornecedorService fornecedorService = new FornecedorService();
            retorno=fornecedorService.pegarDadosDoFornecedor(SessaoApplication.getInstance().getTokenUser());
        }else if(SessaoApplication.getInstance().getTipoDeUserLogado().equals("customer")){
            ClienteService clienteService = new ClienteService();
            retorno=clienteService.pegarDadosDoCliente(SessaoApplication.getInstance().getTokenUser());
        }
        return retorno;
    }
}
