package com.inovaufrpe.makeparty.cliente.gui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.fornecedor.gui.EditarAnuncioActivity;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.gui.adapter.AnuncioAdapter;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;

import java.util.List;

public class ResultadoRecomendacaoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeLayout;
    private List<Ad> adList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_recomendacao);
        setUpToolbar();
        adList = SessaoApplication.getAdRecomendacao();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewResultadoRecomendacao);
        recyclerView.setLayoutManager(new LinearLayoutManager(ResultadoRecomendacaoActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new AnuncioAdapter(ResultadoRecomendacaoActivity.this, adList, onClickAnuncio()));
// Swipe to Refresh

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

    private AnuncioAdapter.AnuncioOnClickListener onClickAnuncio() {
        return new AnuncioAdapter.AnuncioOnClickListener() {
            @Override
            public void onClickAnuncio(AnuncioAdapter.AnunciosViewHolder holder, int indexAnuncio) {
                Ad c = adList.get(indexAnuncio);
                FiltroAnuncioSelecionado.instance.setAnuncioSelecionado(c);

                Intent intent = new Intent(ResultadoRecomendacaoActivity.this, DetalhesAnuncioActivity.class);
                startActivity(intent);
            }

            @Override
            public void onLongClickAnuncio(AnuncioAdapter.AnunciosViewHolder holder, int indexAnuncio) {

            }
        };
    }

    @Override
    public void onBackPressed() {
        SessaoApplication.setAdRecomendacao(null);
        finish();
    }
}
