package com.inovaufrpe.makeparty.cliente.gui;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.dominio.Rating;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.fornecedor.gui.CriarAnuncioActivity;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.user.servico.ValidacaoGuiRapida;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class AvaliacaoNotaActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private TextView txtValorAvaliacao,textViewNomeFornecedorAvaliado;
    private Button botaoAvaliar;
    private Double nota;
    private Integer notaInt;
    private ImageView imageViewFornecedorAvaliar;
    private EditText comentarioAvaliacao;
    private ProgressDialog mprogressDialog;
    private String validar = "";
    private ValidacaoGuiRapida validacaoGuiRapida = new ValidacaoGuiRapida();
    private boolean isValido = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_nota);
        SessaoApplication.getInstance().setTelaAtual(AvaliacaoNotaActivity.class);
        setRatingBar();
        addListenerOnButton();
        setUpToolbar();
        resgatarFotoENomeAnuncioAvaliado();
        mostrarNotaAtual();
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
    public void resgatarFotoENomeAnuncioAvaliado(){
        imageViewFornecedorAvaliar = findViewById(R.id.imageViewFornecedorAvaliar);
        //parametro abaixo do hamba, nos usamos uma classe filtro p pegar o titulo q tinha sido selecionado na lista, dps pegavamos a img dele e trocavamos
        //imageViewFornecedorAvaliar.setImageBitmap(FiltroTitulo.instance.getTituloSelecionado().getImagemBitmap());
        textViewNomeFornecedorAvaliado=findViewById(R.id.textViewNomeFornecedorAvaliado);
        textViewNomeFornecedorAvaliado.setText(FiltroAnuncioSelecionado.instance.getAnuncioSelecionado().getOwner().getSocialname());
    }
    public void setRatingBar() {
        ratingBar = findViewById(R.id.ratingBarUserAvaliar);
        txtValorAvaliacao = findViewById(R.id.txtValorAvaliacao);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float avaliacao, boolean fromUser) {
                nota = (double) avaliacao;
                notaInt=(int) avaliacao;
                txtValorAvaliacao.setText(String.valueOf(nota));
            }
        });
    }
    public void mostrarNotaAtual() {
        //METODOS DO HAMBA , AINDA P ADAPTAR
        //ServicoTitulo servicoTitulo = new ServicoTitulo();
        //Titulo titulo = FiltroTitulo.instance.getTituloSelecionado();
        //Double avaliacao = servicoTitulo.avaliacaoTituloUsuario(titulo);
        //if (avaliacao != null) {
        //  Float nota = avaliacao.floatValue();
        // txtValorAvaliacao.setText(String.valueOf(avaliacao));
        //ratingBar = findViewById(R.id.ratingBarUserAvaliar);
        //ratingBar.setRating(nota);

    }
    public void addListenerOnButton() {
        //ratingBar = findViewById(R.id.ratingBarMediaPublicoEmGeral);
        botaoAvaliar = findViewById(R.id.botaoAvaliar);
        botaoAvaliar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DO HAMBA, AINDA P ADAPTAR P O NOSSO
                pergSeConfirmaAvaliacaoDoForn();

            }
        });
    }
    public String avaliarETransfEmJson(Ad ads, Double nota, String comentario) {
        comentarioAvaliacao= findViewById(R.id.editTextDescricaoComentAnuncAdd);
        Ad anuncioSelecionado = FiltroAnuncioSelecionado.instance.getAnuncioSelecionado();
        Rating rating =new Rating();
        rating.setAd(anuncioSelecionado.get_id());
        rating.setRating(notaInt);
        comentarioAvaliacao= findViewById(R.id.editTextDescricaoComentAnuncAdd);
        rating.setComment(comentarioAvaliacao.getText().toString().trim());
        Gson gson = new Gson();
        String rat = gson.toJson(rating);
        Log.i("Script", "OLHAAA: "+ rat);
        rat=rat.substring(0,rat.length()-1)+","+"\"token\""+":"+ "\""+SessaoApplication.getInstance().getTokenUser()+"\""+ "}";
        Log.i("Script", "OLHAAA: "+ rat);

        return rat;

    }

    public void pergSeConfirmaAvaliacaoDoForn(){
        SimOuNaoDialog.show(getSupportFragmentManager(), "Você confirma a avaliação para esse fornecedor ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                ///FAZER AQ O SETAR E P ENVIAR ETC
                //avaliar(FiltroTitulo.instance.getTituloSelecionado(), nota);
                mprogressDialog = new ProgressDialog(AvaliacaoNotaActivity.this);
                mprogressDialog.setMessage("Cadastrando avaliação...");
                mprogressDialog.show();
                String jsonParaEnviar =avaliarETransfEmJson(FiltroAnuncioSelecionado.instance.getAnuncioSelecionado(),nota,"nadata");

                try {
                    criarAvaliacaoNoServidor(jsonParaEnviar);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                exibirMensagemSeValidouAvaliacao();
                if(isValido){
                    Toast.makeText(AvaliacaoNotaActivity.this, "Avaliação feita com sucesso " + nota
                            + FiltroAnuncioSelecionado.instance.getAnuncioSelecionado().getOwner().getSocialname(), Toast.LENGTH_SHORT).show();
                    // Toast.makeText(AvaliacaoActivity.this, "Avaliação feita com sucesso para o título: " + FiltroTitulo.instance.getTituloSelecionado().getNome(), Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    mprogressDialog.dismiss();
                }

            }
        });
    }
    private void criarAvaliacaoNoServidor(String json) throws InterruptedException {
        callServer("POST", json);
    }
    private void atualizarNotaNoServidor(String json) throws InterruptedException {
        callServer("PUT", json);
    }

    private void callServer(final String method, final String data) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (method.equals("POST")){
                    validar = ConectarServidor.post("https://makepartyserver.herokuapp.com/ratings", data);
                    Log.i("Script", "OLHAAA: " + validar);
                    if (validar.substring(2, 5).equals("err")) {
                        // Não sei qual o erro
                        validar = "Não foi possível avaliar o fornecedor";
                        // Rever a mensagem
                    } else {
                        validar = "Avaliação criada com sucesso";
                        isValido = true;
                    }
                }else if(method.equals("PUT")){

                }
            }
        });
        thread.start();
        thread.join();
    }
    private void exibirMensagemSeValidouAvaliacao() {
        makeText(getApplicationContext(), validar, LENGTH_SHORT).show();
    }
}
