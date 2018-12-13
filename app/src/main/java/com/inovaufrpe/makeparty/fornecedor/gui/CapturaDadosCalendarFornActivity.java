package com.inovaufrpe.makeparty.fornecedor.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Agendamento;
import com.inovaufrpe.makeparty.fornecedor.dominio.Event;
import com.inovaufrpe.makeparty.fornecedor.gui.adapter.AgendamentosFornecedorAdapter;
import com.inovaufrpe.makeparty.fornecedor.gui.fragment.EventsFragment;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.gui.fragment.AnunciosOutroFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CapturaDadosCalendarFornActivity extends AppCompatActivity {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Date date;
    private List<Event> events = new ArrayList<>();
    private Button btCriarEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_dados_calendar_forn);
        SessaoApplication.getInstance().setTelaAtual(CapturaDadosCalendarFornActivity.class);
        pegarIntent();
        setUpToolbar();
        configurarTela();
        criarFragment(savedInstanceState);
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

    // capturando dado
    private void pegarIntent(){
        Intent intent = getIntent();
        Long dateSelected = intent.getLongExtra("dataLongMiliseconds", 0);
        date = new Date(dateSelected);
    }

    private void configurarTela(){
        TextView txt = (TextView) findViewById(R.id.textView1);
        txt.setText(sdf.format(date));
        btCriarEvento = findViewById(R.id.criarEvento);
        btCriarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irTelaCriarEvento();
            }
        });
    }
    private void criarFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            EventsFragment frag = new EventsFragment();
            //AnunciosFragment frag = new AnunciosFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.containerMeusEventos, frag).commit();
        }
    }

    private void irTelaCriarEvento(){
        Intent intent = new Intent(CapturaDadosCalendarFornActivity.this, CriarEventoFornActivity.class);
        intent.putExtra("timeLong", date.getTime());
        startActivity(intent);
    }
}
