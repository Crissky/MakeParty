package com.inovaufrpe.makeparty.cliente.gui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.infra.utils.Permissoes;
import com.inovaufrpe.makeparty.user.gui.EntrarOuCadastrarActivity;
import com.inovaufrpe.makeparty.user.gui.EscolhaTipoUserActivity;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.infra.SessaoApplication;

public class AtualizarPerfilClienteActivity extends AppCompatActivity {

    private ImageView fotoAvatarCliente;
    private FloatingActionButton mudarFoto;
    private Button mudarNome,mudarEmail,mudarSenha,apagarConta,sair;
    private String validar = "";
    private boolean isValido = false;
    private ProgressDialog mprogressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar_perfil_cliente);
        SessaoApplication.getInstance().setTelaAtual(AtualizarPerfilClienteActivity.class);
        setUpToolbar();
        procurandoViews();
    }
    protected void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void procurandoViews(){

        fotoAvatarCliente = findViewById(R.id.ImageViewAvatarCliente);
        mudarFoto = findViewById(R.id.floatingMudarFotoPerfilCliente);
        mudarNome = findViewById(R.id.button_modificar_nome_cliente);
        mudarEmail = findViewById(R.id.button_modificar_email_cliente);
        mudarSenha = findViewById(R.id.button_modificar_senha_cliente);
        apagarConta = findViewById(R.id.button_modificar_apagar_conta_cliente);
        sair = findViewById(R.id.button_modificar_sair_cliente);

    }
    //Metodos da foto ainda falta setar e enviar para a API, fora retornar a img da API(CASO LOGADO) no icone do user
    public void telaMudarFotoCliente(View view) {
        // this.mudarTela();
        Boolean resultadopermissao =permissoesParaCamera();
        if (resultadopermissao){
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i,0);
        }
    }
    //Metodo ainda ta dando erros
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (data !=null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                //Recupera o bitmap retornado pela câmera
                Bitmap bitmap = (Bitmap) bundle.get("data");
                //Atualiza a imagem na tela
                fotoAvatarCliente.setImageBitmap(bitmap);
            }
        }

    }
    //lista de permissoes para a camera e chamada se a permissao foi ok ou n
    public boolean permissoesParaCamera(){
        String permissions[] = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
        };
        boolean ok = Permissoes.validate(this, 0, permissions);
        return ok;
    }
    @Override
    //Mensagem para a caso a lista de permissões seja negada por exemplo
    public void onRequestPermissionsResult(int requestCode, String permissions[],
                                           int[] grantResults){
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this, "É preciso autorizar o uso da camera para tirar a sua foto", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        }
    }

    public void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }

    public void telaMudarNomeCliente(View view) {
        // this.mudarTela();
    }

    public void telaMudarEmailCliente(View view) {
        //this.mudarTela();
    }

    public void telaMudarSenhaCliente(View view) {
        //this.mudarTela();

    }

    public void telaApagarContaCliente(View view) {
        SimOuNaoDialog.show(getSupportFragmentManager(),"Deseja mesmo apagar sua conta?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                mprogressDialog = new ProgressDialog(AtualizarPerfilClienteActivity.this);
                mprogressDialog.setMessage("Apagando conta...");
                mprogressDialog.show();
                String tokenClienteASerExcluido ="{" + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
                try{
                    acaoApagandoConta(tokenClienteASerExcluido);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                exibirMensagemSeValidou();
                if (isValido){
                    mudarTela(EscolhaTipoUserActivity.class);
                }
                mprogressDialog.dismiss();

            }
        });
    }
    private void exibirMensagemSeValidou() {
        Toast.makeText(getApplicationContext(), validar, Toast.LENGTH_SHORT).show();
    }

    public void telaSairCliente(View view) {
        SimOuNaoDialog.show(getSupportFragmentManager(),"Deseja mesmo sair da sua conta?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                SessaoApplication.instance.onTerminate();
                String qmsetado= SessaoApplication.getInstance().getTipoDeUserLogado();
                Log.d("qmsetado",qmsetado);
                mudarTela(EntrarOuCadastrarActivity.class);
                Log.d("qmsetado2",qmsetado);

            }
        });
    }

    @Override // por enq dando o back e só fechando
    public void onBackPressed() {
        this.mudarTela(TelaInicialClienteActivity.class);

    }
    private void mudarFotoPerfil(String json) throws InterruptedException{
        callServer("PUT",json);
    }
    private void acaoApagandoConta(String json) throws InterruptedException{
        callServer("DELETE",json);
    }

    private void callServer(final String method, final String data) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (method.equals("PUT")){
                    //validar = ConectarServidor.put("https://makepartyserver.herokuapp.com/customers", data);
                    Log.i("Script", "OLHAAA: "+ validar);
                }else if(method.equals("DELETE")){
                    //validar = ConectarServidor.delete("https://makepartyserver.herokuapp.com/customers", data);
                    Log.i("Script", "OLHAAA: "+ validar);
                }
                if (validar.substring(2, 5).equals("err")){
                    // Não sei qual o erro
                    validar = "Não foi possível realizar essa ação";
                    // Rever a mensagem
                }else{
                    validar = "Ação realizada com sucesso";
                    isValido = true;
                }
            }
        });
        thread.start();
        thread.join();
    }
}
