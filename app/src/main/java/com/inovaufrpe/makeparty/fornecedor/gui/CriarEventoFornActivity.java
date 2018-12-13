package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Event;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.dominio.Address;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CriarEventoFornActivity extends AppCompatActivity {
    private TextView data,textViewDataNTermMsmDiaDigDataFim;
    private RadioGroup group, tipo;
    private Button criar;
    private EditText obs, hInicio, mInicio, hFim, mFim, endereco, nomeCliente;
    private EditText ruaIdCriarEvForn,numeroCriarEvForn,
            bairroCriarEvForn,cidadeCriarEvForn,cepCriarEvForn,dataFimCriarEventoForn;

    private Date date = new Date(), dateInicio = new Date(), dateFim = new Date();
    private CheckBox checkBoxHorFimAteOutroDia;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String validar = "";
    private boolean isValido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_evento_forn);
        SessaoApplication.getInstance().setTelaAtual(CriarEventoFornActivity.class);
        encontrandoViews();
        Bundle bundle = getIntent().getExtras();
        date.setTime(bundle.getLong("timeLong"));
        data.setText(sdf.format(date));
        tempoEventoRadioGroupListener();
        tipoEventoRadioGroupListener();
    }
    private void encontrandoViews(){
        data = findViewById(R.id.dataSelecionadaCriarEventoForn);
        group = findViewById(R.id.radioGroupSelecHorCriarEventoForn);
        tipo = findViewById(R.id.tipoGroupCriarEventoForn);
        obs = findViewById(R.id.obsCriarEventoForn);
        hInicio = findViewById(R.id.editTextHoraInicioCriandoEvento);
        hFim = findViewById(R.id.horaFimCriarEventoForn);
        mInicio = findViewById(R.id.editTextMinInicioCriandoEvento);
        mFim = findViewById(R.id.minFimCriarEventoForn);
        endereco = findViewById(R.id.addressCriarEventoForn);
        nomeCliente = findViewById(R.id.nomeClienteDoCriarEventoFornSeta);
        checkBoxHorFimAteOutroDia = findViewById(R.id.checkBoxPergCriarEventoHorFimOutroDia);
        textViewDataNTermMsmDiaDigDataFim=findViewById(R.id.textViewDataNTermMsmDiaDigDataFim);
        dataFimCriarEventoForn = findViewById(R.id.dataFimCriarEventoForn);
        ruaIdCriarEvForn =findViewById(R.id.editTextRuaIdCriarEvForn);
        bairroCriarEvForn =findViewById(R.id.editTextBairroCriarEvForn);
        cidadeCriarEvForn =findViewById(R.id.editTextCidadeCriarEvForn);
        cepCriarEvForn =findViewById(R.id.editTextCepCriarEvForn);
        numeroCriarEvForn =findViewById(R.id.editTextNumeroCriarEvForn);
        acoesButton();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    private void acoesButton(){
        criar = findViewById(R.id.criarEvForn);
        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCriar();
            }
        });
        if (checkBoxHorFimAteOutroDia.isSelected()){
            textViewDataNTermMsmDiaDigDataFim.setVisibility(View.VISIBLE);
            dataFimCriarEventoForn.setVisibility(View.VISIBLE);
        }else{
            textViewDataNTermMsmDiaDigDataFim.setVisibility(View.GONE);
            dataFimCriarEventoForn.setVisibility(View.GONE);
        }
    }
    private void tempoEventoRadioGroupListener(){
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.RadioButtonParteDiaCriarEv){
                    findViewById(R.id.inicioEventoFornCriar).setVisibility(View.VISIBLE);
                    findViewById(R.id.fimEventoFornLinearGroup).setVisibility(View.VISIBLE);
                }else if (checkedId == R.id.RadioButtonTodoDiaCriarEv){
                    findViewById(R.id.inicioEventoFornCriar).setVisibility(View.GONE);
                    findViewById(R.id.fimEventoFornLinearGroup).setVisibility(View.GONE);
                }
            }
        });
    }

    private void tipoEventoRadioGroupListener(){
        tipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.RadioGrouptipoEventoCriarEventoForn){
                    findViewById(R.id.eventoLayout).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.eventoLayout).setVisibility(View.GONE);
                }
            }
        });
    }

    private void clickCriar(){
        pergSeConfirmaCriacaoEventoDoForn();
       if (tipo.getCheckedRadioButtonId() == R.id.RadioGrouptipoEventoCriarEventoForn){
           criarEvento();
       }else {
           criarInd();
       }
    }

    private void criarEvento(){
        if (group.getCheckedRadioButtonId() == R.id.RadioButtonParteDiaCriarEv) {
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
        Address address = new Address();
        /*
        address.setStreet();
        address.setNumber();
        address.setZipcode();
        address.setNeighborhood();
        address.setCity();
        address.setState();
        event.setAddress(address);
        */
        Log.d("DATAINICIOCOMOTA",event.getStartDate().toString());
        Log.d("DataFIMCOMOTA",event.getEndDate());
        //MUDAR ESSA LINHA AQUI EM BAIXO:
       // event.setEndereco(endereco.getText().toString().trim());
        pergSeConfirmaCriacaoEventoDoForn();
        Toast.makeText(this, "Foi", Toast.LENGTH_SHORT).show();
    }

    private void criarInd(){
        if (group.getCheckedRadioButtonId() == R.id.RadioButtonParteDiaCriarEv) {
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
        Log.d("DATAINICIOCOMOTA",event.getStartDate().toString());
        Log.d("DataFIMCOMOTA",event.getEndDate());
        Address address = new Address();
        /*
        address.setStreet();
        address.setNumber();
        address.setZipcode();
        address.setNeighborhood();
        address.setCity();
        address.setState();
        event.setAddress(address);
        */
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

    } private void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }

    public void pergSeConfirmaCriacaoEventoDoForn(){
        SimOuNaoDialog.show(getSupportFragmentManager(), "Você confirma os dados e a criação desse evento ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {


            }
        });
    }
    private void criarEventoNoServidor(String json) throws InterruptedException{
        callServer("POST",json);
    }

    private void callServer(final String method, final String data) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                validar = ConectarServidor.post("https://makepartyserver.herokuapp.com/events", data);
                Log.i("Script", "OLHAAA: "+ validar);
                if (validar.substring(2, 5).equals("err")){
                    // Não sei qual o erro
                    validar = "Não foi possível criar o evento";
                    // Rever a mensagem
                }else{
                    validar = "Evento criado com sucesso";
                    isValido = true;
                }
            }
        });
        thread.start();
        thread.join();
    }

    private void exibirMensagemSeValidouCriacaoEvento() {
        Toast.makeText(getApplicationContext(), validar, Toast.LENGTH_SHORT).show();
    }
}
