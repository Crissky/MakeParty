package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.inovaufrpe.makeparty.R;

public class EscolhasSobreAnuncioActivity extends AppCompatActivity {
    private Button btVisualAnuncio,btCriarAnuncio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolhas_sobre_anuncio);
        procurandoViews();
    }
    private void procurandoViews(){
        btVisualAnuncio=findViewById(R.id.buttonVisualizarMeusAnunciosForn);
        btCriarAnuncio = findViewById(R.id.buttonCriarAnuncioEs);
        btVisualAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EscolhasSobreAnuncioActivity.this, AnunciosFornecedorActivity.class));
            }
        });
        btCriarAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EscolhasSobreAnuncioActivity.this,CriarAnuncioActivity.class));
            }
        });
    }
}
