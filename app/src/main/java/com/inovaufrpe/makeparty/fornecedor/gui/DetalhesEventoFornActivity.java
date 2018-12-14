package com.inovaufrpe.makeparty.fornecedor.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.fornecedor.dominio.Event;
import com.inovaufrpe.makeparty.fornecedor.gui.adapter.FiltroEventoSelecionado;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.gui.CadastroActivity;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetalhesEventoFornActivity extends AppCompatActivity {
    private TextView nomeClienteEventoSelecionado, dataInicioEventoSelecionado,
            dataFimTerminaEmQueDiaEventoSelecionado, dataFimEventoSelecionado, tipoEventoFornSelecionado,
            enderecoEventoForn, descricaoEventoFornSelecionado;
    private Button btAtualizarEventoForn, btExcluirEventoForn;
    private SimpleDateFormat sdfServerPatern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private String validar = "";
    private boolean isValido = false;
    private ProgressDialog mprogressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_evento_forn);
        SessaoApplication.getInstance().setTelaAtual(DetalhesEventoFornActivity.class);
        procurandoViews();
        setandoAcoesBotoes();
    }

    private void procurandoViews() {
        setUpToolbar();
        nomeClienteEventoSelecionado = findViewById(R.id.textViewNomeClienteEventoSelecionado);
        dataInicioEventoSelecionado = findViewById(R.id.textViewDataInicioEventoSelecionado);
        dataFimEventoSelecionado = findViewById(R.id.textViewDataFimEventoSelecionado);
        dataFimTerminaEmQueDiaEventoSelecionado = findViewById(R.id.textViewDataFimTerminaEmQueDiaEventoSelecionado);
        tipoEventoFornSelecionado = findViewById(R.id.textViewTipoEouIEventoSelecionado);
        enderecoEventoForn = findViewById(R.id.textViewDescricaoEventoSelecionado);
        descricaoEventoFornSelecionado = findViewById(R.id.textViewDescricaoEventoSelecionado);
        btAtualizarEventoForn = findViewById(R.id.buttonEditarEventoForn);
        btExcluirEventoForn = findViewById(R.id.buttonExcluirEventoForn);
        setandoInfoDetalhes();
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

    private void setandoAcoesBotoes() {
        btAtualizarEventoForn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudarTela(EditarEventoActivity.class);
            }
        });

        btExcluirEventoForn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirEvento();
            }
        });
    }
    private void setandoInfoDetalhes(){
        Event eventoSelecionado= FiltroEventoSelecionado.instance.getEventoSelecionado();
        nomeClienteEventoSelecionado.setText(("Nome do cliente :"+eventoSelecionado.getClient()));
        try {
            Date dateInicio = sdfServerPatern.parse(eventoSelecionado.getStartdate());
            Date dateFim = sdfServerPatern.parse(eventoSelecionado.getEnddate());
            SimpleDateFormat sdfDiaMesAno = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfHoraMin = new SimpleDateFormat("HH:mm");
            String diaMesAnoInicioEvento = sdfDiaMesAno.format(dateInicio);
            String horaMinInicioEvento = sdfHoraMin.format(dateInicio);
            dataInicioEventoSelecionado.setText(("Data de inicio: " + diaMesAnoInicioEvento + " às " + horaMinInicioEvento));
            String diaMesAnoFimEvento = sdfDiaMesAno.format(dateFim);
            String horaMinFimEvento = sdfHoraMin.format(dateFim);
            dataFimEventoSelecionado.setText(("Data de fim: " + diaMesAnoFimEvento + " às " + horaMinFimEvento));

        }catch (ParseException p){
            Log.d("ParseException", p.getMessage());
        }
        tipoEventoFornSelecionado.setText(("Tipo/título do evento :"+eventoSelecionado.getType()));
        enderecoEventoForn.setText(("Endereço : "+eventoSelecionado.getAddress().getStreet()
                +", "+ "Bairro :"+eventoSelecionado.getAddress().getNeighborhood()+", "+ "Número :"
                +eventoSelecionado.getAddress().getNumber()+", "+"Cidade :" +eventoSelecionado.getAddress().getCity()
                //+","+anuncioSelecionado.getAddress().getState()
                +", CEP :"+eventoSelecionado.getAddress().getZipcode()));
        descricaoEventoFornSelecionado.setText(("Descrição/Obs.:"+eventoSelecionado.getDescription()));

    }

    private void excluirEvento() {
        final Ad anuncioSelecionado = FiltroAnuncioSelecionado.instance.getAnuncioSelecionado();
        SimOuNaoDialog.show(getSupportFragmentManager(), "Deseja mesmo excluir esse anúncio ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                String tokenJsonAmao = ","+"\"token\":"+ "\""+ SessaoApplication.getInstance().getTokenUser() +"\""+"}";
                String jsonAnuncioParaDeletarComToken = "{"+"\"_id\":"+ "\""+ anuncioSelecionado.get_id()+"\""+tokenJsonAmao;
                mprogressDialog = new ProgressDialog(DetalhesEventoFornActivity.this);
                mprogressDialog.setMessage("Por favor espere, excluindo anúncio");
                mprogressDialog.show();
                try{
                    excluirEventoOficialNoServ(jsonAnuncioParaDeletarComToken);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                exibirMsgSeValidouExclusaoEvento();

                if (isValido) {
                    mudarTela(CapturaDadosCalendarFornActivity.class);
                }
            }
        });

    }
    private void excluirEventoOficialNoServ(String json) throws InterruptedException{
        callServer("DELETE",json);
    }

    private void callServer(final String method, final String data) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                validar = ConectarServidor.deleteDeJadiel("https://makepartyserver.herokuapp.com/events", data);
                Log.i("Script", "OLHAAA: "+ validar);
                if (validar.substring(2, 5).equals("err")){
                    // Não sei qual o erro
                    validar = "Não foi possível excluir o evento";
                    // Rever a mensagem
                }else{
                    validar = "Evento excluido com sucesso";
                    isValido = true;
                    }
                }
        });
        thread.start();
        thread.join();
    }
    public void exibirMsgSeValidouExclusaoEvento(){
        Toast.makeText(getApplicationContext(), validar, Toast.LENGTH_SHORT).show();
    }


    public void mudarTela(Class tela) {
        Intent intent = new Intent(this, tela);
        startActivity(intent);
        finish();
    }

}

