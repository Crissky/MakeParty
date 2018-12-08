package com.inovaufrpe.makeparty.fornecedor.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;

public class PlanoEscolhaActivity extends AppCompatActivity {
    private ImageView versaoGratuita, planoBronzeMensal,planoBronzeAnual,planoPrataMensal,planoPrataAnual;
    private ImageView planoOuroMensal,planoOuroAnual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plano_escolha);
        SessaoApplication.getInstance().setTelaAtual(PlanoEscolhaActivity.class);
        setTela();
    }

    private void setTela() {
        planoBronzeMensal = findViewById(R.id.planoBronzeMensalId);
        planoBronzeAnual = findViewById(R.id.planoBronzeAnualId);
        planoPrataMensal = findViewById(R.id.planoPrataMensalId);
        planoPrataAnual = findViewById(R.id.planoPrataAnualId);
        planoOuroMensal = findViewById(R.id.planoOuroMensalId);
        planoOuroAnual = findViewById(R.id.planoOuroAnualId);
        versaoGratuita = findViewById(R.id.versaoGratuitaId);

    }
    public void comparacoesPlanoAntigoENovo(){
        versaoGratuita.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                atualizarPlanoDeAnuncioFornecedor();
            }
        });
        planoBronzeMensal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                atualizarPlanoDeAnuncioFornecedor();
            }
        });
        planoBronzeAnual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                atualizarPlanoDeAnuncioFornecedor();
            }
        });


    }

    private void onClickImage(ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //mudar plano
            }
        });

    }
    public void atualizarPlanoDeAnuncioFornecedor(){
        String planoAntigo="null";
        String planoClicado="null";
        SimOuNaoDialog.show(getSupportFragmentManager(),"Deseja confirmar a mudança do plano" +planoAntigo+" -> "+planoClicado, new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                //
                //
                showProgressDialogWithTitle("Por favor, espere", "atualizando dados do anúncio");
                //msgToast("Plano de anúncios modificado com sucesso");
                //msgToast("Erro");
                //mudarTela(ConfiguracoesFornecedorActivity.class);
            }
        });
    }
    public void msgToast(String msgToast){
        Toast.makeText(this, msgToast, Toast.LENGTH_SHORT).show();
        finish();
    }
    public void showProgressDialogWithTitle(String title, String msgEmbaixo) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msgEmbaixo);
        progressDialog.show();
    }
    public void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }


    // por enquanto apenas fechando
    @Override
    public void onBackPressed() {
        finish();
    }
}
