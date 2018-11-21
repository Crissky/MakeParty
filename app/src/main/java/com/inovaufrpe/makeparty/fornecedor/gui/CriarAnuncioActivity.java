package com.inovaufrpe.makeparty.fornecedor.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Anuncio;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.usuario.dominio.Endereco;

import java.util.Date;

public class CriarAnuncioActivity extends AppCompatActivity {
    private EditText edtTitulo, edtValor, edtDescricao, edtTags, edtTelefone, edtRua, edtNumero, edtBairro, edtCidade, edtCep;
    private Spinner edtTipoAnuncio;
    private Button cadastroAnuncio;
    private String validar = "";
    private boolean isValido = false;
    private ProgressDialog mprogressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_anuncio);
        opcoesSpinner();
        setTela();
//        cadastrando();
    }
    public void opcoesSpinner(){
        // Ainda tem que setar isso daqui
    }

    public void setTela(){
        edtTitulo = findViewById(R.id.editTextTitulo);
        edtValor = findViewById(R.id.editTextValor);
        edtDescricao = findViewById(R.id.editTextDescricao);
        edtTags = findViewById(R.id.editTextTags);
        edtTelefone = findViewById(R.id.editTextTelefone);
        edtRua = findViewById(R.id.RuaId);
        edtNumero = findViewById(R.id.editTextNumero);
        edtBairro = findViewById(R.id.editTextBairro);
        edtCidade = findViewById(R.id.editTextCidade);
        edtCep = findViewById(R.id.editTextCep);
        cadastroAnuncio = findViewById(R.id.criarAnuncioId);
        edtTipoAnuncio = findViewById(R.id.spinnertipoAnuncio);
    }

    private void onClickCadastrar(){
        mprogressDialog = new ProgressDialog(CriarAnuncioActivity.this);
        mprogressDialog.setMessage("Cadastrando anúncio...");
        mprogressDialog.show();
        // Fazer verificação de campos
        // Isso aqui embaixo fica dentro do if de validação
        String anuncio = setarAnuncio();
        try{
            cadastrar(anuncio);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }// if acaba aqui
        mprogressDialog.dismiss();
        exibirMensagemSeValidouCadastro();
    }

    public String setarAnuncio(){
//        tentando desvendar a api pra saber como cadastrar
        String cidade = edtCidade.getText().toString().trim();
        String bairro = edtBairro.getText().toString().trim();
        String cep = edtCep.getText().toString().trim();
        String rua = edtRua.getText().toString().trim();
        String numero = edtNumero.getText().toString().trim();

        Endereco endereco = new Endereco();
        endereco.setCity(cidade);
        endereco.setNeighborhood(bairro);
        endereco.setZipcode(cep);
        endereco.setStreet(rua);
        endereco.setNumber(numero);

        String titulo = edtTitulo.getText().toString().trim();
        double valor = Double.parseDouble(edtValor.getText().toString().trim());
        String descricao = edtDescricao.getText().toString().trim();
        String telefone = edtTelefone.getText().toString().trim();
        String tipo = edtTipoAnuncio.getSelectedItem().toString();
        // Tem que ver como vai fazer pra guardar as tags

        Anuncio anuncio = new Anuncio();
        anuncio.setTitle(titulo);
        anuncio.setPrice(valor);
        anuncio.setDataAd(new Date());
        anuncio.setDescription(descricao);
        anuncio.setPhone(telefone);
        anuncio.setType(tipo);
        anuncio.setAddress(endereco);

        Gson gson = new Gson();
        String ad = gson.toJson(anuncio);
        return ad;
    }

    private void cadastrar(String json) throws InterruptedException{
        callServer("POST",json);
    }

    private void callServer(final String method, final String data) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                validar = ConectarServidor.post("https://makepartyserver.herokuapp.com/ads", data);
                if (validar.substring(2, 5).equals("err")){
                    // Não sei qual o erro
                    validar = "Não foi possível criar o anúncio";
                    // Rever a mensagem
                }else{
                    validar = "Anúncio criado com sucesso";
                    isValido = true;
                }
            }
        });
        thread.start();
        thread.join();
    }

    private void exibirMensagemSeValidouCadastro() {
        Toast.makeText(getApplicationContext(), validar, Toast.LENGTH_SHORT).show();

    }

    private void mudarTela(Class proximaTela){
        Intent intent = new Intent( CriarAnuncioActivity.this, proximaTela);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        //mudarTela( INSERIR AQUI A TELA );
    }
}
