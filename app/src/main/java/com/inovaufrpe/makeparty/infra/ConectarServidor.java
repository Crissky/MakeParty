package com.inovaufrpe.makeparty.infra;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.utils.IOUtils;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpDeleteHC4;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class ConectarServidor {
    private final String TAG = "Http";
    public final int TIMEOUT_MILLIS = 15000;
    public boolean LOG_ON = false;
    private String contentType;
    private String charsetToEncode;

    public String doGet(String url) throws IOException {
        return doGet(url, null, "UTF-8");
    }

    public String doGet(String url, Map<String, String> params, String charset) throws IOException {
        String queryString = getQueryString(params);
        if (queryString != null && queryString.trim().length() > 0) {
            url += "?" + queryString;
        }

        if (LOG_ON) {
            Log.d(TAG, ">> Http.doGet: " + url);
        }

        URL u = new URL(url);
        HttpURLConnection conn = null;
        String s = null;
        try {
            conn = (HttpURLConnection) u.openConnection();
            if (contentType != null) {
                conn.setRequestProperty("Content-Type", contentType);
            }
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);
            conn.connect();
            InputStream in = null;
            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d(TAG, "Error code: " + status);
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }
            s = IOUtils.toString(in, charset);
            if (LOG_ON) {
                Log.d(TAG, "<< Http.doGet: " + s);
            }
            in.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return s;
    }

    public String doDelete(String url) throws IOException {
        return doDelete(url, null, "UTF-8");
    }

    public String doDelete(String url, Map<String, String> params, String charset) throws IOException {
        String queryString = getQueryString(params);
        if (queryString != null && queryString.trim().length() > 0) {
            url += "?" + queryString;
        }

        if (LOG_ON) {
            Log.d(TAG, ">> Http.doDelete: " + url);
        }

        URL u = new URL(url);
        HttpURLConnection conn = null;
        String s = null;
        try {
            conn = (HttpURLConnection) u.openConnection();
            if (contentType != null) {
                conn.setRequestProperty("Content-Type", contentType);
            }
            conn.setRequestMethod("DELETE");
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);

            conn.connect();
            InputStream in = conn.getInputStream();
            s = IOUtils.toString(in, charset);
            if (LOG_ON) {
                Log.d(TAG, "<< Http.doGet: " + s);
            }
            in.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return s;
    }


    //METODO POST ABAIXO DOS MENINOS - BRUNO, ITALO, ETC - USA APACHE E THREAD
    public static String post(String completeUrl, String body) {
        HttpClient httpClient = new DefaultHttpClient();
        String answer;
        HttpPost httpPost = new HttpPost(completeUrl);
        httpPost.setHeader("Content-type", "application/json");

        try {
            StringEntity stringEntity = new StringEntity(body);
            httpPost.getRequestLine();
            httpPost.setEntity(stringEntity);

            HttpResponse resposta = httpClient.execute(httpPost);
            answer = EntityUtils.toString(resposta.getEntity());
            Log.i("Script", "ANSWER: " + answer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return answer;
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void sendPutTeste(String json, URL url) throws Exception {

        try {

            // Cria um objeto HttpURLConnection:;
            HttpURLConnection request = (HttpURLConnection)
                    url.openConnection();

            try {
                // Define que a conexão pode enviar informações e obtê-las de volta:
                request.setDoOutput(true);
                request.setDoInput(true);

                // Define o content-type:
                request.setRequestProperty("Content-Type", "application/json");

                // Define o método da requisição:
                request.setRequestMethod("PUT");

                // Conecta na URL:
                request.connect();

                // Escreve o objeto JSON usando o OutputStream da requisição:
                //esse bugger dai parece q so pega a partir do kitkat 19/
                try (OutputStream outputStream = new BufferedOutputStream(request.getOutputStream())) {
                    outputStream.write(json.getBytes("UTF-8"));
                    outputStream.flush();
                    outputStream.close();

                }
                int response = request.getResponseCode();
                BufferedReader br;
                if (200 <= response && response <= 299) {
                    //Requisição feita com sucesso
                }  else {
                    br = new BufferedReader(new InputStreamReader((request.getErrorStream())));
                    String resul = br.readLine();
                    throw new Exception(" Dados da requisição: " + resul);

                }
        } finally {
            request.disconnect();
        }

    } catch (Exception e) {
        throw (e);
    }

}
    public static String putURLconnectionTeste(String completeUrl, String body){
        //ta errado umas coisas ainda
        URL url = null;
        try {
            url = new URL(completeUrl);
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }
        HttpURLConnection httpURLConnection = null;
        DataOutputStream dataOutputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // Define o content-type:
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            // Define o método da requisição:
            httpURLConnection.setRequestMethod("PUT");
            // Define que a conexão pode enviar informações e obtê-las de volta:
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeChars(body); //??? foi eu q coloquei essa linha, se ta certo? n sei
            //dataOutputStream.write("hello");
        } catch (IOException exception) {
            exception.printStackTrace();
        }  finally {
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.flush();
                    dataOutputStream.close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        return "nadaaaaaaaaaaaaaaaaaaaa";
    }
    public static String deleteMen(String completeUrl, String body) {
        HttpClient httpClient = new DefaultHttpClient();
        String answer;
        HttpDelete httpDelete = new HttpDelete(completeUrl);
        httpDelete.setHeader("Content-type", "application/json");

        try {
            StringEntity stringEntity = new StringEntity(body);
            httpDelete.getRequestLine();
            //httpDelete.set
            //httpDelete.setEntity(stringEntity);

            HttpResponse resposta = httpClient.execute(httpDelete);
            answer = EntityUtils.toString(resposta.getEntity());
            Log.i("Script", "ANSWER: " + answer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return answer;
    }

    private String inserirDocServer(String... strings) {
        String jsonResposta = null;

        try {
            URL url = new URL("https://makepartyserver.herokuapp.com/ads");
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestMethod("post");
            conexao.addRequestProperty("Content-type", "application/json");
            conexao.setRequestProperty("authorization", SessaoApplication.instance.getTokenUser());

            conexao.setDoOutput(true);
            conexao.setDoInput(true);

            PrintStream printStream = new PrintStream(conexao.getOutputStream());
            //printStream.println(strings[ZERO]);

            conexao.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder sbHtml = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                sbHtml.append(linha);
            }
            jsonResposta = sbHtml.toString();
            reader.close();
            printStream.close();
            conexao.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //usuarioService.setRespostaServidor(jsonResposta);

        return jsonResposta;
    }

    private String deleteDeJadiel(String completeUrl, String body) {
        String jsonResposta = null;

        try {
            URL url = new URL(completeUrl);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

            conexao.setRequestMethod("delete");
            conexao.addRequestProperty("Content-type", "application/json");
            conexao.setRequestProperty("authorization", "\"token\"" + ":\"" + SessaoApplication.instance.getTokenUser() + "\"");

            conexao.setDoOutput(true);
            conexao.setDoInput(true);

            PrintStream printStream = new PrintStream(conexao.getOutputStream());
            //printStream.println(strings[ZERO]);

            conexao.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder sbHtml = new StringBuilder();
            String linha;

            while ((linha = reader.readLine()) != null) {
                sbHtml.append(linha);
            }
            jsonResposta = sbHtml.toString();
            reader.close();
            printStream.close();
            conexao.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        //usuarioService.setRespostaServidor(jsonResposta);

        return jsonResposta;
    }

    public static String postComToken(String url, String body) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json");
        httpPost.addHeader("Authorization", SessaoApplication.instance.getTokenUser());
        //ESSE METODO POSTCOMTOKENTADANDOERRADOO
        String answer;
        try {
            StringEntity stringEntity = new StringEntity(body);
            httpPost.getRequestLine();
            httpPost.setEntity(stringEntity);
            HttpResponse resposta = httpClient.execute(httpPost);
            int status = resposta.getStatusLine().getStatusCode();
            if (status == 201) {
                answer = EntityUtils.toString(resposta.getEntity());
                Log.i("Script", "ANSWER: " + answer);
            } else if (status == 409) {
                throw new Exception("Mr já existe");
            } else if (status == 403) {
                answer = EntityUtils.toString(resposta.getEntity());
                Log.i("Script", "ANSWER: " + answer);
            } else if (status == 200) {
                answer = EntityUtils.toString(resposta.getEntity());
                Log.i("Script", "ANSWER: " + answer);
            } else {
                throw new Exception("Erro inesperado");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Log.i("resp", answer);

        return answer;
    }

    //dos men
    private static String put(String url, String body) {
        HttpClient httpClient = new DefaultHttpClient();
        HttpPut httpPut = new HttpPut(url);
        httpPut.setHeader("Content-type", "application/json");
        //httpPut.addHeader("Authorization", "Bearer "+ Sessao.instance.getSession().getToken());
        String answer = null;
        String erro = null;
        try {
            StringEntity stringEntity = new StringEntity(body);
            httpPut.getRequestLine();
            httpPut.setEntity(stringEntity);
            HttpResponse resposta = httpClient.execute(httpPut);

            int status = resposta.getStatusLine().getStatusCode();
            if (status == 200) {
                answer = EntityUtils.toString(resposta.getEntity());
                SessaoApplication.instance.setResposta("Sucesso");
                Log.i("Put", "ANSWER: " + answer);
//            } else if (status == 404) {
//                Erro = "Sailor não encontrado";
//                Log.i("Put", "Sailor não encontrado, Status - " + status);
//                throw new Exception(Erro);
//            } else if(status == 400) {
//                Erro = "Email em uso";
//                Log.i("Put", "Email em uso, Status - " + status);
//                throw new Exception(Erro);
            } else {
                SessaoApplication.instance.setResposta("Error");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return answer;
    }


    public String doPost(String url, Map<String, String> params, String charset) throws IOException {
        String queryString = getQueryString(params);
        byte[] bytes = params != null ? queryString.getBytes(charset) : null;
        if (LOG_ON) {
            Log.d(TAG, "Http.doPost: " + url + "?" + params);
        }
        return doPost(url, bytes, charset);
    }

    public String doPost(String url, byte[] params, String charset) throws IOException {
        if (LOG_ON) {
            Log.d(TAG, ">> Http.doPost: " + url);
        }

        URL u = new URL(url);
        HttpURLConnection conn = null;
        String s = null;
        try {
            conn = (HttpURLConnection) u.openConnection();
            if (contentType != null) {
                conn.setRequestProperty("Content-Type", contentType);
            }
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(TIMEOUT_MILLIS);
            conn.setReadTimeout(TIMEOUT_MILLIS);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            if (params != null) {
                OutputStream out = conn.getOutputStream();
                out.write(params);
                out.flush();
                out.close();
            }
            InputStream in = null;
            int status = conn.getResponseCode();
            if (status >= HttpURLConnection.HTTP_BAD_REQUEST) {
                Log.d(TAG, "Error code: " + status);
                in = conn.getErrorStream();
            } else {
                in = conn.getInputStream();
            }
            s = IOUtils.toString(in, charset);
            if (LOG_ON) {
                Log.d(TAG, "<< Http.doPost: " + s);
            }
            in.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
        return s;
    }

    public Bitmap doGetBitmap(String url) throws IOException {
        if (LOG_ON) {
            Log.d(TAG, ">> Http.doGet: " + url);
        }
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(TIMEOUT_MILLIS);
        conn.setReadTimeout(TIMEOUT_MILLIS);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.connect();
        InputStream in = conn.getInputStream();
        byte[] bytes = IOUtils.toBytes(in);
        if (LOG_ON) {
            Log.d(TAG, "<< Http.doGet: " + bytes);
        }
        in.close();
        conn.disconnect();
        Bitmap bitmap = null;
        if (bytes != null) {
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return bitmap;
    }

    /**
     * Retorna a QueryString para 'GET'
     */
    public String getQueryString(Map<String, String> params) throws IOException {
        if (params == null || params.size() == 0) {
            return null;
        }
        String urlParams = null;
        for (String chave : params.keySet()) {
            Object objValor = params.get(chave);
            if (objValor != null) {
                String valor = objValor.toString();
                if (charsetToEncode != null) {
                    valor = URLEncoder.encode(valor, charsetToEncode);
                }
                urlParams = urlParams == null ? "" : urlParams + "&";
                urlParams += chave + "=" + valor;
            }
        }
        return urlParams;

    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setCharsetToEncode(String encode) {
        this.charsetToEncode = encode;
    }
}
