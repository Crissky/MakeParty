package com.inovaufrpe.makeparty.cliente.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;

public class AvaliacaoNotaActivity extends AppCompatActivity {
    private RatingBar ratingBar;
    private TextView txtValorAvaliacao;
    private Button botaoAvaliar;
    private Double nota;
    private ImageView imageViewFornecedorAvaliar;
    private EditText comentarioAvaliacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_nota);
        SessaoApplication.getInstance().setTelaAtual(AvaliacaoNotaActivity.class);
        setRatingBar();
        addListenerOnButton();
        setUpToolbar();
        resgatarFotoTituloAvaliado();
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
    public void setRatingBar() {
        ratingBar = findViewById(R.id.ratingBarUserAvaliar);
        txtValorAvaliacao = findViewById(R.id.txtValorAvaliacao);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float avaliacao, boolean fromUser) {
                nota = (double) avaliacao;
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
    public void avaliar(Ad ads, Double nota, String comentario) {
        comentarioAvaliacao= findViewById(R.id.editTextDescricaoComentAnuncAdd);
        Ad anuncioSelecionado = FiltroAnuncioSelecionado.instance.getAnuncioSelecionado();
        //TUDO DO HAMBA , AINDA P ADAPTAR
        //ServicoTitulo servicoTitulo = new ServicoTitulo();
        //servicoTitulo.avaliar(ads, nota);
    }

    public void resgatarFotoTituloAvaliado(){
        imageViewFornecedorAvaliar = findViewById(R.id.imageViewFornecedorAvaliar);
        //parametro abaixo do hamba, nos usamos uma classe filtro p pegar o titulo q tinha sido selecionado na lista, dps pegavamos a img dele e trocavamos
        //imageViewFornecedorAvaliar.setImageBitmap(FiltroTitulo.instance.getTituloSelecionado().getImagemBitmap());
    }
    public void pergSeConfirmaAvaliacaoDoForn(){
        SimOuNaoDialog.show(getSupportFragmentManager(), "Você confirma a avaliação para esse fornecedor ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                ///FAZER AQ O SETAR E P ENVIAR ETC
                //avaliar(FiltroTitulo.instance.getTituloSelecionado(), nota);
                Toast.makeText(AvaliacaoNotaActivity.this, "Avaliação feita com sucesso " + nota
                        + FiltroAnuncioSelecionado.instance.getAnuncioSelecionado().getOwner().getSocialname(), Toast.LENGTH_SHORT).show();
                // Toast.makeText(AvaliacaoActivity.this, "Avaliação feita com sucesso para o título: " + FiltroTitulo.instance.getTituloSelecionado().getNome(), Toast.LENGTH_SHORT).show();
                finish();


            }
        });
    }
}
