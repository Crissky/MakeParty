package com.inovaufrpe.makeparty.fornecedor.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Owner;
import com.inovaufrpe.makeparty.fornecedor.dominio.Plano;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;

public class PlanoEscolhaActivity extends AppCompatActivity {
    private ImageView versaoGratuita, planoBronzeMensal,planoBronzeAnual,planoPrataMensal,planoPrataAnual;
    private ImageView planoOuroMensal,planoOuroAnual;
    private String validar = "";
    private boolean isValido = false;

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
        comparacoesPlanoAntigoENovo();
    }
    public void comparacoesPlanoAntigoENovo(){
        versaoGratuita.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                atualizarPlanoDeAnuncioFornecedor("Plano Gratuito");
            }
        });
        planoBronzeMensal.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                atualizarPlanoDeAnuncioFornecedor("Plano Bronze Mensal");
            }
        });
        planoBronzeAnual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                atualizarPlanoDeAnuncioFornecedor("Plano Bronze Anual");
            }
        });
        planoPrataMensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarPlanoDeAnuncioFornecedor("Plano Prata Mensal");
            }
        });
        planoPrataAnual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarPlanoDeAnuncioFornecedor("Plano Prata Anual");
            }
        });
        planoOuroMensal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarPlanoDeAnuncioFornecedor("Plano Ouro Mensal");
            }
        });
        planoOuroAnual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarPlanoDeAnuncioFornecedor("Plano Ouro Anual");
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
    public void atualizarPlanoDeAnuncioFornecedor(final String planoClicado){
        final Owner owner = SessaoApplication.getInstance().getObjOwnerSeEleForTipoLogado();
        final String planoAntigo="null";
        SimOuNaoDialog.show(getSupportFragmentManager(),"Deseja confirmar a aquisição do " +planoClicado, new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                //
                //
                showProgressDialogWithTitle("Por favor, espere", "atualizando dados do anúncio");
                Plano plano;
                if (owner.getPlan() == null){
                    plano = new Plano();
                    plano.setType(planoClicado);
                    plano.setTotalad(0);
                    plano.setTotalphoto(0);
                }else {
                    plano = owner.getPlan();
                    plano.setType(planoClicado);
                }
                owner.setPlan(plano);
                Gson gson = new Gson();
                String newOwner = gson.toJson(owner);
                newOwner = newOwner.substring(0, newOwner.length() - 1) + "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
                showProgressDialogWithTitle("Por favor, espere", "atualizando dados do perfil");
                try {
                    editarPlano(newOwner);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isValido){
                    msgToast("Plano de anúncios modificado com sucesso");
                    mudarTela(ConfiguracoesFornecedorActivity.class);
                }else {
                    msgToast("Erro");
                }
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

    public void editarPlano(String json) throws InterruptedException {
        callServer("PUT", json);
    }

    private void callServer(final String method, final String data) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (method.equals("PUT")) {
                    validar = ConectarServidor.putJadiel("https://makepartyserver.herokuapp.com/advertisers", data);
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
}
