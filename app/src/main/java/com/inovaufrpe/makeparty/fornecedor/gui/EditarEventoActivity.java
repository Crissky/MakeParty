package com.inovaufrpe.makeparty.fornecedor.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.inovaufrpe.makeparty.R;

public class EditarEventoActivity extends AppCompatActivity {
    private Button editarEvForn,excluirEvFornTelaEditarEv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento_forn);
    }
}
