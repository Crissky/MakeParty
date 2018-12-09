package com.inovaufrpe.makeparty.fornecedor.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.HorariosDisponiveis;

import java.util.ArrayList;

public class CriarHorarioDisponivelActivity extends AppCompatActivity {
    private EditText hInicio, mInicio, hFim, mFim;
    private CheckBox domingo, segunda, terca, quarta, quinta, sexta, sabado;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_horario_disponivel);
        configurarTela();
    }

    private void configurarTela(){
        hInicio = findViewById(R.id.horaInicio);
        mInicio = findViewById(R.id.minutoInicio);
        hFim = findViewById(R.id.horaFim);
        mFim = findViewById(R.id.minutoFim);
        domingo = findViewById(R.id.checkboxDomingo);
        segunda = findViewById(R.id.checkboxSegunda);
        terca = findViewById(R.id.checkboxTerca);
        quarta = findViewById(R.id.checkboxQuarta);
        quinta = findViewById(R.id.checkboxQuinta);
        sexta = findViewById(R.id.checkboxSexta);
        sabado = findViewById(R.id.checkboxSabado);
        button = findViewById(R.id.buttonCriarHorario);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickButton();
            }
        });
    }

    private void clickButton(){
        if (verificarDias() && verificarHorario()){
            String horario = hInicio.getText().toString() + mInicio.getText().toString() + "-" + hFim.getText().toString() + mFim.getText().toString();
            HorariosDisponiveis disponiveis = new HorariosDisponiveis(); // ao inves de ser new deve pegar do fornecedor
            ArrayList temp;
            if (domingo.isChecked()){
                temp = disponiveis.getDomingo();
                if (temp == null){
                    temp = new ArrayList();
                }
                temp.add(horario);
                disponiveis.setDomingo(temp);
            }if (segunda.isChecked()){
                temp = disponiveis.getSegunda();
                if (temp == null){
                    temp = new ArrayList();
                }
                temp.add(horario);
                disponiveis.setSegunda(temp);
            }if (terca.isChecked()){
                temp = disponiveis.getTerca();
                if (temp == null){
                    temp = new ArrayList();
                }
                temp.add(horario);
                disponiveis.setTerca(temp);
            }if (quarta.isChecked()){
                temp = disponiveis.getQuarta();
                if (temp == null){
                    temp = new ArrayList();
                }
                temp.add(horario);
                disponiveis.setQuarta(temp);
            }if (quinta.isChecked()){
                temp = disponiveis.getQuinta();
                if (temp == null){
                    temp = new ArrayList();
                }
                temp.add(horario);
                disponiveis.setQuinta(temp);
            }if (sexta.isChecked()){
                temp = disponiveis.getSexta();
                if (temp == null){
                    temp = new ArrayList();
                }
                temp.add(horario);
                disponiveis.setSexta(temp);
            }if (sabado.isChecked()){
                temp = disponiveis.getSabado();
                if (temp == null){
                    temp = new ArrayList();
                }
                temp.add(horario);
                disponiveis.setSabado(temp);
            }
        }
    }

    private boolean verificarDias(){
        boolean ok = true;
        if (!(domingo.isChecked() || segunda.isChecked() || terca.isChecked() || quarta.isChecked() || quinta.isChecked() || sexta.isChecked() || sabado.isChecked())){
            ok = false;
        }
        return ok;
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
}
