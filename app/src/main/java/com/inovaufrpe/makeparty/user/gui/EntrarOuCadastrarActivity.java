package com.inovaufrpe.makeparty.user.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.infra.SessaoApplication;

public class EntrarOuCadastrarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SessaoApplication.getInstance().setTelaAtual(EntrarOuCadastrarActivity.class);
        setContentView(R.layout.activity_entrar_ou_cadastrar);
    }

    private void mudarTela(Class proximaTela){
        Intent intent = new Intent( EntrarOuCadastrarActivity.this, proximaTela);
        startActivity(intent);
        finish();
    }

    public void irParaTelaEntrar(View view) {
        this.mudarTela(LoginActivity.class);
    }

    public void irParaTelaCriarConta(View view) {
        this.mudarTela(CadastroActivity.class);
    }
    @Override
    public void onBackPressed() {
        this.mudarTela(EscolhaTipoUserActivity.class);
    }
}
