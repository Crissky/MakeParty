package com.inovaufrpe.makeparty.fornecedor.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.inovaufrpe.makeparty.R;

public class PlanoEscolhaActivity extends AppCompatActivity {
    private ImageView planoBronzeMensal;
    private ImageView planoBronzeAnual;
    private ImageView planoPrataMensal;
    private ImageView planoPrataAnual;
    private ImageView planoOuroMensal;
    private ImageView planoOuroAnual;
    private ImageView versaoGratuita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plano_escolha);
        setTela();
    }
    private void setTela(){
        planoBronzeMensal = findViewById(R.id.planoBronzeMensalId);
        planoBronzeAnual = findViewById(R.id.planoBronzeAnualId);
        planoPrataMensal = findViewById(R.id.planoPrataMensalId);
        planoPrataAnual = findViewById(R.id.planoPrataAnualId);
        planoOuroMensal = findViewById(R.id.planoOuroMensalId);
        planoOuroAnual = findViewById(R.id.planoOuroAnualId);
        versaoGratuita = findViewById(R.id.versaoGratuitaId);
    }
}
