package com.inovaufrpe.makeparty.cliente.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;
import com.inovaufrpe.makeparty.fornecedor.gui.AnunciosFornecedorActivity;
import com.inovaufrpe.makeparty.infra.SessaoApplication;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class DispDiaSelecPeloClienteActivity extends AppCompatActivity {
    TextView textViewDataSelecionadaNaTelaDetalhes;
    FrameLayout containerDiasDisponiveisDoFornDiaSelecPeloCliente;
    Button buttonEnviarSolicReuniao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disp_dia_selec_pelo_cliente);
        procurandoItensView();
    }
    public void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }
    public void procurandoItensView(){
        textViewDataSelecionadaNaTelaDetalhes =findViewById(R.id.textViewDataSelecionadaNaTelaDetalhes);
        containerDiasDisponiveisDoFornDiaSelecPeloCliente =findViewById(R.id.containerDiasDisponiveisDoFornDiaSelecPeloCliente);
        buttonEnviarSolicReuniao = findViewById(R.id.button_enviar_solic_reuniao);
        buttonEnviarSolicReuniao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                acaoButtonEviarSolicReuniao();
            }
        });
        setandoDataSelecionadaAnteriormente();
        procurandoHorariosDispDoFornecNoDiaSelecPeloCliente();
    }

    public void setandoDataSelecionadaAnteriormente(){
        Long diaSelecionadoPeloClienteDisp = FiltroAnuncioSelecionado.instance.getDiaSelecionadoPeloClienteDisp();
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(diaSelecionadoPeloClienteDisp).;
        TextView textView = (TextView) findViewById(R.id.textViewDataSelecionadaNaTelaDetalhes);
        textView.setText(currentDateString);
    }
    public void procurandoHorariosDispDoFornecNoDiaSelecPeloCliente(){
        Ads anuncioSelecionado = FiltroAnuncioSelecionado.instance.getAnuncioSelecionado();
        Long diaSelecionadoPeloClienteDispParaBuscarNaAPI = FiltroAnuncioSelecionado.instance.getDiaSelecionadoPeloClienteDisp();
        //FALTA
        //FALTA RETORNAR REALMENTEEEEEEEEEEEEE
        //PUXAR UM ADAPTER UMA LISTA OU SL AINDA E COLOCAR NO FRAMELAYOUT

    }
    public void acaoButtonEviarSolicReuniao(){

    }
    @Override
    public void onBackPressed() {
      mudarTela(DetalhesAnuncioActivity.class);
    }
}
