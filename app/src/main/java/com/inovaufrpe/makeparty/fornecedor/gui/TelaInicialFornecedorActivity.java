package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.usuario.gui.EscolhaTipoUserActivity;
import com.inovaufrpe.makeparty.usuario.gui.LoginActivity;
import com.inovaufrpe.makeparty.usuario.gui.dialog.SimOuNaoDialog;


public class TelaInicialFornecedorActivity extends AppCompatActivity {
    private ImageView notificacoes;
    private ImageView calendar;
    private ImageView configuracoes;
    private ImageView anuncios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_fornecedor);
        SessaoApplication.getInstance().setTelaAtual(TelaInicialFornecedorActivity.class);
        procurandoViews();
        notificacoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicialFornecedorActivity.this, NotificacoesActivity.class));
            }
        });
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicialFornecedorActivity.this,CalendarActivity.class));
            }
        });
        configuracoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicialFornecedorActivity.this,ConfiguracoesFornecedorActivity.class));
            }
        });
        anuncios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TelaInicialFornecedorActivity.this, AnunciosFornecedorActivity.class));
            }
        });

    }
    private void procurandoViews() {
        notificacoes = findViewById(R.id.notificacoesId);
        calendar = findViewById(R.id.calendarId);
        configuracoes = findViewById(R.id.configuracoesId);
        anuncios = findViewById(R.id.anunciosId);

    }
    public void exibirMsgEAgirSeQuiserSairRealmente(){
        SimOuNaoDialog.show(getSupportFragmentManager(), "Deseja realmente sair da sua conta ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                SessaoApplication.getInstance().onTerminate();
                mudarTela(EscolhaTipoUserActivity.class);
            }
        });
    }
    private void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }

   @Override // por enq dando o back e só fechando
    public void onBackPressed() {
        exibirMsgEAgirSeQuiserSairRealmente();
    }


}
