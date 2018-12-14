package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Event;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.infra.utils.Mask;
import com.inovaufrpe.makeparty.user.dominio.Address;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.user.servico.ValidacaoGuiRapida;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.*;

public class CriarEventoFornActivity extends AppCompatActivity {
    private TextView data, textViewDataNTermMsmDiaDigDataFim;
    private RadioGroup group, tipo;
    private Button criar;
    private EditText obs, hInicio, mInicio, hFim, mFim, endereco;
    private EditText nomeCliente;
    private EditText ruaIdCriarEvForn, numeroCriarEvForn,
            bairroCriarEvForn, cidadeCriarEvForn, cepCriarEvForn, dataFimCriarEventoForn,tituloETipoDoEvento;

    private Date date = new Date(), dateInicio = new Date(), dateFim = new Date();
    private CheckBox checkBoxHorFimAteOutroDia;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String isDataFimOutroDia = "false";
    private String validar = "";
    private ValidacaoGuiRapida validacaoGuiRapida = new ValidacaoGuiRapida();
    private boolean isValido = false;
    private SimpleDateFormat sdfServerPattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

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

    private void encontrandoViews() {
        data = findViewById(R.id.dataSelecionadaCriarEventoForn);
        group = findViewById(R.id.radioGroupSelecHorCriarEventoForn);
        tipo = findViewById(R.id.tipoGroupCriarEventoForn);
        tituloETipoDoEvento = findViewById(R.id.editTextNomeParaEvForn);
        obs = findViewById(R.id.obsCriarEventoForn);
        hInicio = findViewById(R.id.editTextHoraInicioCriandoEvento);
        hFim = findViewById(R.id.horaFimCriarEventoForn);
        mInicio = findViewById(R.id.editTextMinInicioCriandoEvento);
        mFim = findViewById(R.id.minFimCriarEventoForn);
        endereco = findViewById(R.id.addressCriarEventoForn);
        nomeCliente = findViewById(R.id.nomeClienteDoCriarEventoFornSeta);
        checkBoxHorFimAteOutroDia = findViewById(R.id.checkBoxPergCriarEventoHorFimOutroDia);
        textViewDataNTermMsmDiaDigDataFim = findViewById(R.id.textViewDataNTermMsmDiaDigDataFim);
        dataFimCriarEventoForn = findViewById(R.id.dataFimCriarEventoForn);
        dataFimCriarEventoForn.addTextChangedListener(Mask.insert("##/##/####",dataFimCriarEventoForn));
        ruaIdCriarEvForn = findViewById(R.id.editTextRuaIdCriarEvForn);
        bairroCriarEvForn = findViewById(R.id.editTextBairroCriarEvForn);
        cidadeCriarEvForn = findViewById(R.id.editTextCidadeCriarEvForn);
        cepCriarEvForn = findViewById(R.id.editTextCepCriarEvForn);
        numeroCriarEvForn = findViewById(R.id.editTextNumeroCriarEvForn);
        acoesButton();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    private void acoesButton() {
        criar = findViewById(R.id.criarEvForn);
        criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCriar();
            }
        });
        verifCliqueCheckBoxTerminaEventoEmOutroDia();
    }
    private void verifCliqueCheckBoxTerminaEventoEmOutroDia(){
        checkBoxHorFimAteOutroDia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    textViewDataNTermMsmDiaDigDataFim.setVisibility(View.VISIBLE);
                    dataFimCriarEventoForn.setVisibility(View.VISIBLE);
                    isDataFimOutroDia="true";
                } else {
                    textViewDataNTermMsmDiaDigDataFim.setVisibility(View.GONE);
                    dataFimCriarEventoForn.setVisibility(View.GONE);
                    isDataFimOutroDia="false";
                }
            }
        });
    }

    private void tempoEventoRadioGroupListener() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.RadioButtonParteDiaCriarEv) {
                    findViewById(R.id.inicioEventoFornCriar).setVisibility(View.VISIBLE);
                    findViewById(R.id.fimEventoFornLinearGroup).setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.RadioButtonTodoDiaCriarEv) {
                    findViewById(R.id.inicioEventoFornCriar).setVisibility(View.GONE);
                    findViewById(R.id.fimEventoFornLinearGroup).setVisibility(View.GONE);
                }
            }
        });
    }

    private void tipoEventoRadioGroupListener() {
        tipo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.RadioGrouptipoEventoCriarEventoForn) {
                    findViewById(R.id.eventoLayout).setVisibility(View.VISIBLE);
                    tituloETipoDoEvento.setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.eventoLayout).setVisibility(View.GONE);
                    tituloETipoDoEvento.setVisibility(View.GONE);
                }
            }
        });
    }

    private void clickCriar() {
        SimOuNaoDialog.show(getSupportFragmentManager(), "Você confirma os dados e a criação desse evento ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                if (tipo.getCheckedRadioButtonId() == R.id.RadioGrouptipoEventoCriarEventoForn) {
                    criarEvento();
                } else {
                    criarInd();
                }
            }
        });
    }

    private void criarEvento() {
        if (!verficarCamposParaEvento()){
            return;
        }
        if (group.getCheckedRadioButtonId() == R.id.RadioButtonParteDiaCriarEv) {
            if ((verificarHorario())) {
                montarHorario();
            } else {
                return;
            }
        } else {
            montarDiaTodo();
        }
        Event event = new Event();
        event.setDescription(obs.getText().toString().trim());
        event.setStartdate(sdfServerPattern.format(dateInicio));
        event.setEnddate(sdfServerPattern.format(dateFim));
        event.setType(tituloETipoDoEvento.getText().toString().trim());
        event.setAdvertiser(SessaoApplication.getInstance().getObjOwnerSeEleForTipoLogado().get_id());
        event.setClient(nomeCliente.getText().toString().trim());
        Address address = new Address();

        address.setStreet(ruaIdCriarEvForn.getText().toString().trim());
        address.setNumber(numeroCriarEvForn.getText().toString().trim());
        address.setZipcode(cepCriarEvForn.getText().toString().trim());
        address.setNeighborhood(bairroCriarEvForn.getText().toString().trim());
        address.setCity(cidadeCriarEvForn.getText().toString().trim());
        address.setState("Pernambuco");
        event.setAddress(address);

        Log.d("DATAINICIOCOMOTA", event.getStartdate().toString());
        Log.d("DataFIMCOMOTA", event.getEnddate());
        //MUDAR ESSA LINHA AQUI EM BAIXO:
        Gson gson = new Gson();
        String eventAqui = gson.toJson(event);
        Log.i("Script", "OLHAAA: " + event);
        eventAqui = eventAqui.substring(0, eventAqui.length() - 1) + "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
        Log.i("Script", "OLHAAA: " + eventAqui);
        try {
            criarEventoNoServidor(eventAqui);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exibirMensagemSeValidouCriacaoEvento();
        Log.i("RESPSERV",validar);

    }
    private boolean verficarCamposParaEvento(){
        String titulo = tituloETipoDoEvento.getText().toString().trim();
        //String nomeCliente = nomeCliente.getText().toString().trim();
        String cidade = cidadeCriarEvForn.getText().toString().trim();
        String bairro = bairroCriarEvForn.getText().toString().trim();
        String cep = cepCriarEvForn.getText().toString().trim();
        String rua = ruaIdCriarEvForn.getText().toString().trim();
        String numero = numeroCriarEvForn.getText().toString().trim();
        //String dataEv = dataFimCriarEventoForn
        //String titulo = edtTitulo.getText().toString().trim();

        //String descricao = .getText().toString().trim();

        Boolean camposOk = true;
        if (!validacaoGuiRapida.isCampoAceitavel(titulo)) {
            this.tituloETipoDoEvento.setError(("Digite um título para seu evento"));
            this.tituloETipoDoEvento.requestFocus();
            return false;
        }else if(isDataFimOutroDia.equals("true")){
            String dataEv = dataFimCriarEventoForn.getText().toString().trim().replace("/","");
            boolean campoEspecOk=true;
            if (!validacaoGuiRapida.isDataValida(dataEv)){
                this.dataFimCriarEventoForn.setError(("Data inválida, digite uma data válida"));
                campoEspecOk= false;
            }
            return campoEspecOk;

        }else if(validacaoGuiRapida.isCampoVazio(cidade)){
            this.cidadeCriarEvForn.setError("Favor insira a cidade");
            this.cidadeCriarEvForn.requestFocus();
            return false;
        }else if(validacaoGuiRapida.isCampoVazio(bairro)){
            this.bairroCriarEvForn.setError("Favor insira o Bairro");
            this.bairroCriarEvForn.requestFocus();
            return false;
        }else if(validacaoGuiRapida.isCampoVazio(rua)){
            this.ruaIdCriarEvForn.setError("Favor insira a Rua");
            return false;
        }else if(!validacaoGuiRapida.isCepValido(cep)){
            this.cepCriarEvForn.setError("Favor insira um CEP Válido");
            this.cepCriarEvForn.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    private void criarInd() {
        if (group.getCheckedRadioButtonId() == R.id.RadioButtonParteDiaCriarEv) {
            if (verificarHorario()) {
                montarHorario();
            } else {
                return;
            }
        } else {
            montarDiaTodo();
        }
        Event event = new Event();
        event.setDescription(obs.getText().toString().trim());
        event.setStartdate(sdfServerPattern.format(dateInicio));
        event.setEnddate(sdfServerPattern.format(dateFim));
        event.setType("Indisponibilidade");
        Log.d("DATAINICIOCOMOTA", event.getStartdate().toString());
        Log.d("DataFIMCOMOTA", event.getEnddate());
        event.setAdvertiser(SessaoApplication.getInstance().getObjOwnerSeEleForTipoLogado().get_id());
        event.setClient("vazio");
        Address address = new Address();

        address.setStreet("vazio");
        address.setNumber("vazio");
        address.setZipcode("vazio");
        address.setNeighborhood("vazio");
        address.setCity("vazio");
        address.setState("vazio");
        event.setAddress(address);

        Gson gson = new Gson();
        String eventAqui = gson.toJson(event);
        Log.i("Script", "OLHAAA: " + event);
        eventAqui = eventAqui.substring(0, eventAqui.length() - 1) + "," + "\"token\"" + ":" + "\"" + SessaoApplication.getInstance().getTokenUser() + "\"" + "}";
        Log.i("Script", "OLHAAA: " + eventAqui);
        try {
            criarEventoNoServidor(eventAqui);
        } catch (InterruptedException e) {
            Log.d("OlhaOErroAqui:", e.getMessage());
            e.printStackTrace();
        }
        exibirMensagemSeValidouCriacaoEvento();
    }


    private boolean verificarHorario() {
        boolean ok = true;
        String hi = hInicio.getText().toString();
        String hf = hFim.getText().toString();
        String mi = mInicio.getText().toString();
        String mf = mFim.getText().toString();
        if (hi.isEmpty()) {
            ok = false;
            this.hInicio.setError("Horário de inicio vazio, digite horários válidos");
            this.hInicio.requestFocus();
        } else if (hf.isEmpty()) {
            ok = false;
            this.hFim.setError("Horário de Fim vazio, digite horários válidos");
            this.hFim.requestFocus();
        } else if (mi.isEmpty()) {
            ok = false;
            this.mInicio.setError("Horário de minuto inicial vazio, digite horários válidos");
            this.mInicio.requestFocus();
        } else if (mf.isEmpty()) {
            ok = false;
            this.mFim.setError("Horário de minuto final vazio, digite horários válidos");
            this.mFim.requestFocus();
        }
        if (!ok) {
            return ok;
        }
        hi = "00".substring(hi.length()) + hi;
        hf = "00".substring(hf.length()) + hf;
        mi = "00".substring(mi.length()) + mi;
        mf = "00".substring(mf.length()) + mf;
        int horaI = Integer.parseInt(hi);
        int horaF = Integer.parseInt(hf);
        int minI = Integer.parseInt(mi);
        int minF = Integer.parseInt(mf);
        int hmI = Integer.parseInt(hi + mi);
        int hmF = Integer.parseInt(hf + mf);
        if (horaI > 23 || horaI < 0) {
            ok = false;
            this.hInicio.setError("Hora inválida, digite horários válidos");
            this.hInicio.requestFocus();
        } else if (horaF > 23 || horaF < 0) {
            ok = false;
            this.hFim.setError("Hora inválida, digite horários válidos");
            this.hFim.requestFocus();
        } else if (minI > 59 || minI < 0) {
            ok = false;
            this.mInicio.setError("Minuto inválido, digite horários válidos");
            this.mInicio.requestFocus();
        } else if (minF > 59 || minF < 0) {
            ok = false;
            this.mFim.setError("Minuto inválido, digite horários válidos");
            this.mInicio.requestFocus();
        } else if (hmI >= hmF && isDataFimOutroDia.equals("false")) {
            ok = false;
        }
        return ok;
    }

    private void montarHorario() {
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

    private void montarDiaTodo() {
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

    private void mudarTela(Class tela) {
        Intent intent = new Intent(this, tela);
        startActivity(intent);
        finish();
    }

    public void pergSeConfirmaCriacaoEventoDoForn() {
        SimOuNaoDialog.show(getSupportFragmentManager(), "Você confirma os dados e a criação desse evento ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {


            }
        });
    }

    private void criarEventoNoServidor(String json) throws InterruptedException {
        callServer("POST", json);
    }

    private void callServer(final String method, final String data) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                validar = ConectarServidor.post("https://makepartyserver.herokuapp.com/events", data);
                Log.i("Script", "OLHAAA: " + validar);
                if (validar.substring(2, 5).equals("err")) {
                    // Não sei qual o erro
                    validar = "Não foi possível criar o evento";
                    // Rever a mensagem
                } else {
                    validar = "Evento criado com sucesso";
                    isValido = true;
                }
            }
        });
        thread.start();
        thread.join();
    }

    private void exibirMensagemSeValidouCriacaoEvento() {
        makeText(getApplicationContext(), validar, LENGTH_SHORT).show();
    }
}