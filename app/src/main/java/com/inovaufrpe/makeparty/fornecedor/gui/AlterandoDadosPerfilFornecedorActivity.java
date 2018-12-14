package com.inovaufrpe.makeparty.fornecedor.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.AlterandoDadosPerfilClienteActivity;
import com.inovaufrpe.makeparty.cliente.gui.ConfigClienteActivity;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.user.servico.ValidacaoGuiRapida;

public class AlterandoDadosPerfilFornecedorActivity extends AppCompatActivity {

    public EditText nomeAlterar;
    public EditText emailAlterar;
    public EditText senhaAtual;
    public EditText novaSenhaAlterar;
    public Button botaoAlterarPerfil;
    public boolean isValido = false;
    private String validar = "";
    ValidacaoGuiRapida validacaoGuiRapida = new ValidacaoGuiRapida();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterando_dados_perfil_fornecedor);
        SessaoApplication.getInstance().setTelaAtual(AlterandoDadosPerfilClienteActivity.class);
        setUpToolbar();
        setTela();
        acaoAlterar();
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

    public void setTela() {
        nomeAlterar = findViewById(R.id.editTextNomeAlterandoPerfilForn);
        emailAlterar = findViewById(R.id.editTextEmailAlterandoPerfilForn);
        senhaAtual = findViewById(R.id.editTextSenhaAtualAlterandoPerfil);
        novaSenhaAlterar = findViewById(R.id.editTextNovaNovaSenhaAlterandoPerfilForn);
        botaoAlterarPerfil = findViewById(R.id.btAtualizandoDadosForn);
    }

    public void acaoAlterar() {
        botaoAlterarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cliqueAtualizarPerfil();
            }
        });
    }

    public void msgToast(String msgToast) {
        Toast.makeText(this, msgToast, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void exibirMsgSeAlterou() {
        Toast.makeText(getApplicationContext(), validar, Toast.LENGTH_SHORT).show();
    }

    public void showProgressDialogWithTitle(String title, String msgEmbaixo) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msgEmbaixo);
        progressDialog.show();
    }

   /* public Customer retornandoPerfilComNovosDadosParaAtualizar() {
        Customer dadosPerfilSelecionadoAntesEdicao = SessaoApplication.instance.getObjCustomerSeEleForTipoLogado();
        //User user = SessaoApplication.instance.getUser();
        //Customer perfilASerEnviadoPUT = new Customer();
        perfilASerEnviadoPUT.setName(emailAlterar.getText().toString());
       // perfilASerEnviadoPUT.setPassword(novaSenhaAlterar.getText().toString());
        return perfilASerEnviadoPUT;

    }*/

    public boolean verificarCampos() {
        String email = emailAlterar.getText().toString().trim();
        // String senha = novaSenhaAlterar.getText().toString().trim();
        Boolean camposOk = true;
        if (!validacaoGuiRapida.isEmailValido(email)) {
            this.emailAlterar.setError(("Digite um email válido"));
            this.emailAlterar.requestFocus();
            return false;
            // } else if (!validacaoGuiRapida.isSenhaValida(senha)) {
            //   this.novaSenhaAlterar.setError("Digite uma senha válida");
            // this.novaSenhaAlterar.requestFocus();
            //return false;
        } else
            return true;
    }

    public void editarPerfil(String json) throws InterruptedException {
        callServer("PUT", json);
    }

    private void callServer(final String method, final String data) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (method.equals("PUT")) {
                    validar = ConectarServidor.putJadiel("https://makepartyserver.herokuapp.com/customers", data);
                    Log.i("Script", "OLHAAA: " + validar);
                    if (validar.substring(2, 5).equals("err")) {
                        validar = "Não foi possível editar o perfil";
                    } else {
                        validar = "Perfil editado com sucesso";
                        isValido = true;
                    }
                }
            }
        });
        thread.start();
        thread.join();
    }

    public void cliqueAtualizarPerfil(){
        SimOuNaoDialog.show(getSupportFragmentManager(), "Deseja confirmar a atualização desse perfil?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                if(verificarCampos()){
                    //Customer perfil = retornandoPerfilComNovosDadosParaAtualizar();
                    Gson gson = new Gson();
                    // String perfilParaAtualizar = gson.toJson(perfil);
                    // perfilParaAtualizar = perfilParaAtualizar.substring(0, perfilParaAtualizar.length() - 1) + "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
                    //Log.i("Script", "OLHAAA: " + perfil);
                    showProgressDialogWithTitle("Por favor, espere", "atualizando dados do perfil");
                    /*try {
                        editarPerfil(perfilParaAtualizar);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
                exibirMsgSeAlterou();
                if (isValido) {
                    msgToast("Perfil atualizado com sucesso");
                    mudarTela(ConfigClienteActivity.class);
                } else {
                    msgToast("Erro"); }
            }
        });
    }
    public void exibirMsgSeValidouAlteracaoDados(){
        Toast.makeText(getApplicationContext(), validar, Toast.LENGTH_SHORT).show();
    }
    public void mudarTela(Class tela) {
        Intent intent = new Intent(this, tela);
        startActivity(intent);
        finish();
    }

    public void onBackPressed() {
        this.mudarTela(ConfiguracoesFornecedorActivity.class);

    }

}
