package com.inovaufrpe.makeparty.fornecedor.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CriarIndisponibilidadeActivity extends AppCompatActivity {
    private TextView data;
    private RadioGroup group, tipo;
    private Button criar;
    private EditText obs, hInicio, mInicio, hFim, mFim, endereco, nomeCliente;
    private Date date = new Date(), dateInicio = new Date(), dateFim = new Date();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_indisponibilidade);

        data = findViewById(R.id.data);
        group = findViewById(R.id.radioGroup);
        tipo = findViewById(R.id.tipoGroup);
        criar = findViewById(R.id.criar);
        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCriar();
            }
        });
        obs = findViewById(R.id.obs);
        hInicio = findViewById(R.id.horaInicio);
        hFim = findViewById(R.id.horaFim);
        mInicio = findViewById(R.id.minInicio);
        mFim = findViewById(R.id.minFim);
        endereco = findViewById(R.id.address);
        nomeCliente = findViewById(R.id.nomeCliente);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Bundle bundle = getIntent().getExtras();
        date.setTime(bundle.getLong("timeLong"));
        data.setText(sdf.format(date));
        tempoRadioGroupListener();
        tipoRadioGroupListener();
    }

    private void tempoRadioGroupListener(){
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.parteDia){
                    findViewById(R.id.inicio).setVisibility(View.VISIBLE);
                    findViewById(R.id.fim).setVisibility(View.VISIBLE);
                }else if (checkedId == R.id.todoDia){
                    findViewById(R.id.inicio).setVisibility(View.GONE);
                    findViewById(R.id.fim).setVisibility(View.GONE);
                }
            }
        });
    }

    private void tipoRadioGroupListener(){
        tipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.tipoEvento){
                    findViewById(R.id.eventoLayout).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.eventoLayout).setVisibility(View.GONE);
                }
            }
        });
    }

    private void clickCriar(){
       if (tipo.getCheckedRadioButtonId() == R.id.tipoEvento){
           criarEvento();
       }else {
           criarInd();
       }
    }

    private void criarEvento(){
        if (group.getCheckedRadioButtonId() == R.id.parteDia) {
            if (verificarHorario()) {
                montarHorario();
            }else {
                return;
            }
        }else {
            montarDiaTodo();
        }
        Event event = new Event();
        event.setDescription(obs.getText().toString().trim());
        event.setStartDate(String.valueOf(dateInicio));
        event.setEndDate(String.valueOf(dateFim));
        //MUDAR ESSA LINHA AQUI EM BAIXO:
       // event.setEndereco(endereco.getText().toString().trim());
        Toast.makeText(this, "Foi", Toast.LENGTH_SHORT).show();
    }

    private void criarInd(){
        if (group.getCheckedRadioButtonId() == R.id.parteDia) {
            if (verificarHorario()) {
                montarHorario();
            }else {
                return;
            }
        }else {
            montarDiaTodo();
        }
        Event event = new Event();
        event.setDescription(obs.getText().toString().trim());
        event.setStartDate(String.valueOf(dateInicio));
        event.setEndDate(String.valueOf(dateFim));
        Toast.makeText(this, "Foi", Toast.LENGTH_SHORT).show();
    }

    private boolean verificarHorario(){
        boolean ok = true;
        String hi = hInicio.getText().toString();
        String hf = hFim.getText().toString();
        String mi = mInicio.getText().toString();
        String mf = mFim.getText().toString();
        if (hi.isEmpty()){
            ok = false;
        }else if (hf.isEmpty()){
            ok = false;
        }else if (mi.isEmpty()){
            ok = false;
        }else if (mf.isEmpty()){
            ok = false;
        }if (!ok){
            return ok;
        }
        int horaI = Integer.parseInt(hi);
        int horaF = Integer.parseInt(hf);
        int minI = Integer.parseInt(mi);
        int minF = Integer.parseInt(mf);
        int hmI = Integer.parseInt(hi + mi);
        int hmF = Integer.parseInt(hf + mf);
        if (horaI > 23 || horaI < 0){
            ok = false;
        }else if (horaF > 23 || horaF < 0){
            ok = false;
        }else if (minI > 59 || minI < 0){
            ok = false;
        }else if (minF > 59 || minF < 0) {
            ok = false;
        }else if (hmI >= hmF){
            ok = false;
        }return ok;
    }

    private void montarHorario(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hInicio.getText().toString()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(mInicio.getText().toString()));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        dateInicio.setTime(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hFim.getText().toString()));
        calendar.set(Calendar.MINUTE, Integer.parseInt(mFim.getText().toString()));
        dateFim.setTime(calendar.getTimeInMillis());
    }

    private void montarDiaTodo(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        dateInicio.setTime(calendar.getTimeInMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        dateFim.setTime(calendar.getTimeInMillis());
    }
}
