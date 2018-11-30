package com.inovaufrpe.makeparty.cliente.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.fragment.AnunciosOutroFragment;

public class ListaDesejosClienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_desejos_cliente);
        setUpToolbar();
    }
    protected void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
    private void criarFragment(Bundle savedInstanceState) {
        //getSupportActionBar().setTitle(getString(getIntent().getIntExtra("tipo",6)));
        if (savedInstanceState == null) {
            AnunciosOutroFragment frag = new AnunciosOutroFragment();
            //AnunciosFragment frag = new AnunciosFragment();
            frag.setArguments(getIntent().getExtras());
            //getSupportFragmentManager().beginTransaction().add(R.id.container, frag).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.containerFAV, frag).commit();
        }
    }
    public void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        this.mudarTela(TelaInicialClienteActivity.class);

    }
}
