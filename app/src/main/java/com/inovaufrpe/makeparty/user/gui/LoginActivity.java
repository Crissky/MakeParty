package com.inovaufrpe.makeparty.user.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.TelaInicialClienteActivity;
import com.inovaufrpe.makeparty.fornecedor.gui.TelaInicialFornecedorActivity;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.dominio.User;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.ServicoDownload;
import com.inovaufrpe.makeparty.user.servico.UsuarioService;
import com.inovaufrpe.makeparty.user.servico.ValidacaoGuiRapida;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class LoginActivity extends AppCompatActivity{
    private EditText edtEmail, edtSenha;
    private ProgressDialog progressDialog;
    private ProgressDialog dialog;
    private ValidacaoGuiRapida validacaoGuiRapida = new ValidacaoGuiRapida();
    private String tipoUserLogou ="";
    private String resgateDadosJsonRespServ ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        encontrandoElementosView();

    }
    private void encontrandoElementosView(){
        edtEmail= findViewById(R.id.editTextEmail);
        edtSenha= findViewById(R.id.editTextSenha);

    }
    //Por enq só pegando os dados e transf em string, sem chamar o serviço
    public void onClickLogar(View view){
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();
        try {
            login();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    private void login() throws InterruptedException {
        showProgressDialogWithTitle();
        if (this.verificarCampos()) {
            if(isOnline()){
                String usuario = setarUsuario(edtEmail.getText().toString().trim(), edtSenha.getText().toString().trim());
                logar(usuario);
                //Toast.makeText(this, Sessao.instance.getResposta(), Toast.LENGTH_SHORT).show();
                if(SessaoApplication.instance.getResposta().contains("E-mail ou senha incorretos")) {
                    Toast.makeText(this, "E-mail ou senha incorretos", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else if(SessaoApplication.instance.getResposta().substring(2, 5).equals("err")) {
                    Toast.makeText(this, "Não encontramos o seu cadastro", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                } else {
                    //getSessaoApi();
                    String[] parts = SessaoApplication.instance.getResposta().split(",");
                    String token = parts[0].substring(10,parts[0].length()-1); //FALTA  setar o usuario logado
                    tipoUserLogou = parts[1].substring(8,parts[1].length()-2);
                    SessaoApplication.instance.setTokenUser(token);
                    SessaoApplication.instance.setTipoDeUserLogado(tipoUserLogou);
                    SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy HH:mm-",new Locale("pt","BR"));
                    //Date data = new Date();
                    java.util.Date dataUtil = new java.util.Date();
                    //String dataFormatada = formataData.format(data);
                    SessaoApplication.instance.setHoraRecebidoToken(dataUtil);
                    //Toast.makeText(this, tipoUserLogou, Toast.LENGTH_SHORT).show();
                    pesquisarDados(SessaoApplication.getInstance().getTokenUser());
                    if (resgateDadosJsonRespServ.substring(2, 5).equals("err")){
                        Toast.makeText(this, "Erro ao procurar seus dados", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, "Logado", Toast.LENGTH_SHORT).show();
                        if (tipoUserLogou.equals("customer")){
                            irParaTelaInicialCliente();
                        }else {
                            irParaTelaInicialFornecedor();
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }else{
            progressDialog.dismiss();
        }

    }

    private void logar(String jason)  throws InterruptedException {
        callServer( "POST",jason);
    }
    private void pesquisarDados(String token)  throws InterruptedException {
            callServer( "GET",token);
    }

    private void callServer(final String method, final String data) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (method.equals("POST")) {
                    SessaoApplication.instance.setResposta(ConectarServidor.post("https://makepartyserver.herokuapp.com/users/authenticate", data));
                    Log.i("Script", "OLHAAA: " + SessaoApplication.instance.getResposta());
                }else if(method.equals("GET")){
                    UsuarioService usuarioService = new UsuarioService();
                    try {
                        //Para resgatar , fazer verif .getTipoDeUserLogado, dependendo se for customer ou adversiter ai pode chamar o metodo
                        //p pegar ownerIsLogado ou customerIsLogado q eles retornam o objeto
                        resgateDadosJsonRespServ = usuarioService.verificarTipoUserPegarDadosDeleEGuardarNaSessao(data);
                        SessaoApplication.instance.setResposta(resgateDadosJsonRespServ);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        thread.join();
    }
    private boolean verificarCampos() {
        String email = this.edtEmail.getText().toString().trim();
        String senha = this.edtSenha.getText().toString().trim();
        if (!validacaoGuiRapida.isEmailValido(email)) {
            this.edtEmail.setError("Email ou senha inválidos");
            return false;
        } else if (!validacaoGuiRapida.isSenhaValida(senha)) {
            this.edtSenha.setError("E-mail ou senha inválidos");
            return false;
        } else {
            return true;
        }
    }

    private String setarUsuario(String email, String senha){
        User usuario = new User();
        usuario.setPassword(senha);
        usuario.setEmail(email);
        Gson gson = new Gson();
        String user = gson.toJson(usuario);

        return user;
    }

    private boolean isOnline() {
        if(ServicoDownload.isNetworkAvailable(getApplicationContext())) {
            return true;
        }else{
            return false;
        }
    }
    public void showProgressDialogWithTitle() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Por favor espere..");
        progressDialog.setMessage("Verificando dados ...");
        progressDialog.show();
    }
    private void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }
    public void irParaTelaInicialCliente(){
        this.mudarTela(TelaInicialClienteActivity.class);
    }
    public void irParaTelaInicialFornecedor(){
        this.mudarTela(TelaInicialFornecedorActivity.class);
    }
    @Override
    public void onBackPressed(){
        this.mudarTela(EntrarOuCadastrarActivity.class);

    }
}
