package com.inovaufrpe.makeparty.servico;

import android.os.AsyncTask;
import java.net.HttpURLConnection;



import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.net.URL;
import java.util.Objects;


public class ConexaoServidor extends AsyncTask<String, String, String> {


    private UsuarioService usuarioService = new UsuarioService();


//requisição HTTP
    private String conectarUser(String... strings){
        String jsonResposta = null;
        try{
            URL url = new URL(usuarioService.getUrlCriarUsuario());
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("POST");
            conexao.addRequestProperty("Content-type", "application/json");

            conexao.setDoOutput(true);
            conexao.setDoInput(true);

            PrintStream printStream = new PrintStream(conexao.getOutputStream());
            printStream.println();

            conexao.connect();

            BufferedReader reader = new BufferedReader( new InputStreamReader( conexao.getInputStream()));

            conexao.disconnect();
            StringBuilder sbHtml = new StringBuilder();
            String linha;

            while( ( linha = reader.readLine() ) != null )
            {
                sbHtml.append (linha);
            }
            jsonResposta = sbHtml.toString();
            reader.close();
            printStream.close();
            conexao.disconnect();

        }catch(Exception e){
            e.printStackTrace();
        }
        usuarioService.setRespostaServidor(jsonResposta);

        return jsonResposta;
    }


//Thread para das requisições
    @Override  // Este método é usado para exibir qualquer forma de progresso na interface do usuário, enquanto a tarefa ainda está em execução.
    protected String doInBackground(String... strings) {
        String jsonResposta = null;

        jsonResposta = conectarUser(strings);
        return jsonResposta;
    }

    //@Override // Este passo é usado para configurar a tarefa, por exemplo, mostrando uma barra de progresso na interface do usuário.
    //protected void onPreExecute(){
    //}

    //@Override //O resultado da execução em background é passado para este passo como um parâmetro.
    //protected void onPostExecute(){
    //}
}
