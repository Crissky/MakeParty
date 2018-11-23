package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Anuncio;
import com.inovaufrpe.makeparty.usuario.gui.CadastroActivity;

import java.util.ArrayList;


public class AnunciosFornecedorActivity extends AppCompatActivity {
    private ArrayList<Anuncio> lista;
    private ListView listaAnuncios;
    private Button btCriarAnuncio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios_fornecedor);
        btCriarAnuncio = findViewById(R.id.criarAnuncioId);
        listaAnuncios = findViewById(R.id.listaId);
        btCriarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telacriarAnuncio();
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

   private void telacriarAnuncio(){
        startActivity(new Intent(AnunciosFornecedorActivity.this, CriarAnuncioActivity.class));
        AnunciosFornecedorActivity.this.finish();
   }

}
