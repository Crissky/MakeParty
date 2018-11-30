package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.fragment.AnunciosOutroFragment;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;

import java.util.ArrayList;


public class AnunciosFornecedorActivity extends AppCompatActivity {
    private ArrayList<Ads> lista;
    private ListView listaAnuncios;
    private Button btVisualAnuncio,btCriarAnuncio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios_fornecedor);
        criarFragment(savedInstanceState);
        procurandoViews();

    }
    private void criarFragment(Bundle savedInstanceState) {
        //getSupportActionBar().setTitle(getString(getIntent().getIntExtra("tipo",6)));
        if (savedInstanceState == null) {
            AnunciosOutroFragment frag = new AnunciosOutroFragment();
            //AnunciosFragment frag = new AnunciosFragment();
            frag.setArguments(getIntent().getExtras());
            //getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerMeusAnuncios, frag).commit();
        }
    }

    private void procurandoViews(){
        btVisualAnuncio=findViewById(R.id.buttonVisualizarMeusAnunciosForn);
        btCriarAnuncio = findViewById(R.id.buttonCriarAnuncioEs);
        btCriarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnunciosFornecedorActivity.this,CriarAnuncioActivity.class));
            }
        });
    }
    private void mudarTela(Class proximaTela){
        Intent intent = new Intent( AnunciosFornecedorActivity.this, proximaTela);
        startActivity(intent);
        finish();
    }

    public void irParaTelaCriarAnuncio(View view) {
        this.mudarTela(CriarAnuncioActivity.class);
    }

    private void telacriarAnuncio() {
        startActivity(new Intent(AnunciosFornecedorActivity.this, CriarAnuncioActivity.class));
        AnunciosFornecedorActivity.this.finish();
    }
    @Override
    public void onBackPressed() {
        mudarTela(TelaInicialFornecedorActivity.class);
    }

}
