package com.inovaufrpe.makeparty.fornecedor.gui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Agendamento;
import com.inovaufrpe.makeparty.fornecedor.gui.adapter.AgendamentosFornecedorAdapter;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.infra.utils.AgendamentoBuilder;
import com.inovaufrpe.makeparty.usuario.gui.LoginActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CapturaDadosCalendarActivity extends Activity {
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Date date;
    private List<Agendamento> agendamentos = new ArrayList<>();
    private Button btCriarEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_captura_dados_calendar);
        SessaoApplication.getInstance().setTelaAtual(CapturaDadosCalendarActivity.class);
        pegarIntent();
        setUpToolbar();
        configurarTela();


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
        ListView listView = findViewById(R.id.listView);
        escolherAgendamentos();
        AgendamentosFornecedorAdapter adapter = new AgendamentosFornecedorAdapter(CapturaDadosCalendarActivity.this, agendamentos);
        listView.setAdapter(adapter);
    }

    private void escolherAgendamentos(){
        List<Agendamento> list = AgendamentoBuilder.gerarAgendamentos();
        for (Agendamento agendamento: list){
            if (sdf.format(agendamento.getDateInicio()).equals(sdf.format(date))){
                agendamentos.add(agendamento);
            }
        }
    }

    private void irTelaCriarEvento(){
        Intent intent = new Intent(CapturaDadosCalendarActivity.this, CriarIndisponibilidadeActivity.class);
        intent.putExtra("timeLong", date.getTime());
        startActivity(intent);
    }
}
