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
import android.widget.Toast;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Event;
import com.inovaufrpe.makeparty.fornecedor.gui.adapter.FiltroEventoSelecionado;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.infra.utils.Mask;
import com.inovaufrpe.makeparty.user.dominio.Address;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.user.servico.ValidacaoGuiRapida;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.widget.Toast.makeText;

public class EditarEventoActivity extends AppCompatActivity {
    private TextView data, textViewDataNTermMsmDiaDigDataFim;
    private RadioGroup group, tipo;
    private Button editar;
    private EditText obs, hInicio, mInicio, hFim, mFim, endereco;
    private EditText nomeCliente;
    private EditText ruaIdEditarEvForn, numeroEditarEvForn,
            bairroEditarEvForn, cidadeEditarEvForn, cepEditarEvForn, dataFimEditarEventoForn,tituloETipoDoEvento;

    private Date date = new Date(), dateInicio = new Date(), dateFim = new Date();
    private CheckBox checkBoxHorFimAteOutroDia;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private String isDataFimOutroDia = "false";
    private String validar = "";
    private ValidacaoGuiRapida validacaoGuiRapida = new ValidacaoGuiRapida();
    private boolean isValido = false;
    private SimpleDateFormat sdfServerPattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private Button editarEvForn,excluirEvFornTelaEditarEv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_evento_forn);
        SessaoApplication.getInstance().setTelaAtual(EditarEventoActivity.class);
        encontrandoViews();
        tempoEventoRadioGroupListener();
        tipoEventoRadioGroupListener();
       /* Bundle bundle = getIntent().getExtras();
        date.setTime(bundle.getLong("timeLong"));
        data.setText(sdf.format(date));

        */
    }


    private void encontrandoViews() {
        data = findViewById(R.id.dataSelecionadaEditarEventoForn);
        group = findViewById(R.id.radioGroupSelecHorEditarEventoForn);
        tipo = findViewById(R.id.tipoGroupEditarEventoForn);
        tituloETipoDoEvento = findViewById(R.id.editTextNomeParaEvFornEditar);
        obs = findViewById(R.id.obseEditarEventoForn);
        hInicio = findViewById(R.id.editTextHoraInicioEditandoEventoForn);
        hFim = findViewById(R.id.horaFimEditarEventoForn);
        mInicio = findViewById(R.id.editTextMinInicioEditandoEventoForn);
        mFim = findViewById(R.id.minFimEditarEventoForn);
        endereco = findViewById(R.id.addressEditarEventoForn);
        nomeCliente = findViewById(R.id.nomeClienteDoEditarEventoFornSeta);
        checkBoxHorFimAteOutroDia = findViewById(R.id.checkBoxPergEditarEventoHorFimOutroDia);
        //textViewDataNTermMsmDiaDigDataFim = findViewById(R.id.textViewDataNTermMsmDiaDigDataFim);
        dataFimEditarEventoForn = findViewById(R.id.dataFimEditarEventoForn);
        dataFimEditarEventoForn.addTextChangedListener(Mask.insert("##/##/####",dataFimEditarEventoForn));
        ruaIdEditarEvForn = findViewById(R.id.editTextRuaIdEditarEvForn);
        bairroEditarEvForn = findViewById(R.id.editTextBairroEditarEvForn);
        cidadeEditarEvForn = findViewById(R.id.editTextCidadeEditarEvForn);
        cepEditarEvForn = findViewById(R.id.editTextCepEditarEvForn);
        cepEditarEvForn.addTextChangedListener(Mask.insert("########", cepEditarEvForn));
        numeroEditarEvForn = findViewById(R.id.editTextNumeroEditarEvForn);
        acoesButton();
        procurandoInfoDoEventoAntesDeAtualizar();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
    private void procurandoInfoDoEventoAntesDeAtualizar(){
        Event eventSelecionado = FiltroEventoSelecionado.instance.getEventoSelecionado();
        obs.setText(eventSelecionado.getDescription());
        nomeCliente.setText(eventSelecionado.getClient());
        ruaIdEditarEvForn.setText(eventSelecionado.getAddress().getStreet());
        numeroEditarEvForn.setText(eventSelecionado.getAddress().getNumber());
        cepEditarEvForn.setText(eventSelecionado.getAddress().getZipcode());
        bairroEditarEvForn.setText(eventSelecionado.getAddress().getNeighborhood());
        cidadeEditarEvForn.setText(eventSelecionado.getAddress().getCity());
        tituloETipoDoEvento.setText(eventSelecionado.getType());

        //event.setStartdate(sdfServerPattern.format(dateInicio));
        //event.setEnddate(sdfServerPattern.format(dateFim));
    }

    private void acoesButton() {
        editar = findViewById(R.id.editarEvForn);
        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickEditar();
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
                    dataFimEditarEventoForn.setVisibility(View.VISIBLE);
                    isDataFimOutroDia="true";
                } else {
                    textViewDataNTermMsmDiaDigDataFim.setVisibility(View.GONE);
                    dataFimEditarEventoForn.setVisibility(View.GONE);
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

    private void clickEditar() {
        SimOuNaoDialog.show(getSupportFragmentManager(), "Você confirma os dados e a atualização desse evento ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                if (tipo.getCheckedRadioButtonId() == R.id.RadioGrouptipoEventoCriarEventoForn) {
                    editarEvento();
                } else {
                    editarInd();
                }
            }
        });
    }

    private void editarEvento() {
        if (!verficarCamposParaEvento()) {
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

        address.setStreet(ruaIdEditarEvForn.getText().toString().trim());
        address.setNumber(numeroEditarEvForn.getText().toString().trim());
        address.setZipcode(cepEditarEvForn.getText().toString().trim());
        address.setNeighborhood(bairroEditarEvForn.getText().toString().trim());
        address.setCity(cidadeEditarEvForn.getText().toString().trim());
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
            editarEventoOficialNoServ(eventAqui);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exibirMsgSeValidouAtualizacaoEvento();
        Log.i("RESPSERV", validar);
        if (isValido) {
            mudarTela(CapturaDadosCalendarFornActivity.class);
        }
    }

    private boolean verficarCamposParaEvento(){
        String titulo = tituloETipoDoEvento.getText().toString().trim();
        //String nomeCliente = nomeCliente.getText().toString().trim();
        String cidade = cidadeEditarEvForn.getText().toString().trim();
        String bairro = bairroEditarEvForn.getText().toString().trim();
        String cep = cepEditarEvForn.getText().toString().trim();
        String rua = ruaIdEditarEvForn.getText().toString().trim();
        String numero = numeroEditarEvForn.getText().toString().trim();
        //String dataEv = dataFimCriarEventoForn
        //String titulo = edtTitulo.getText().toString().trim();

        //String descricao = .getText().toString().trim();

        Boolean camposOk = true;
        if (!validacaoGuiRapida.isCampoAceitavel(titulo)) {
            this.tituloETipoDoEvento.setError(("Digite um título para seu evento"));
            this.tituloETipoDoEvento.requestFocus();
            return false;
        }else if(isDataFimOutroDia.equals("true")){
            String dataEv = dataFimEditarEventoForn.getText().toString().trim().replace("/","");
            boolean campoEspecOk=true;
            if (!validacaoGuiRapida.isDataValida(dataEv)){
                this.dataFimEditarEventoForn.setError(("Data inválida, digite uma data válida"));
                campoEspecOk= false;
            }
            return campoEspecOk;

        }else if(validacaoGuiRapida.isCampoVazio(cidade)){
            this.cidadeEditarEvForn.setError("Favor insira a cidade");
            this.cidadeEditarEvForn.requestFocus();
            return false;
        }else if(validacaoGuiRapida.isCampoVazio(bairro)){
            this.bairroEditarEvForn.setError("Favor insira o Bairro");
            this.bairroEditarEvForn.requestFocus();
            return false;
        }else if(validacaoGuiRapida.isCampoVazio(rua)){
            this.ruaIdEditarEvForn.setError("Favor insira a Rua");
            return false;
        }else if(!validacaoGuiRapida.isCepValido(cep)){
            this.cepEditarEvForn.setError("Favor insira um CEP Válido");
            this.cepEditarEvForn.requestFocus();
            return false;
        }else{
            return true;
        }
    }

    private void editarInd() {
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
            editarEventoOficialNoServ(eventAqui);
        } catch (InterruptedException e) {
            Log.d("OlhaOErroAqui:", e.getMessage());
            e.printStackTrace();
        }
        exibirMsgSeValidouAtualizacaoEvento();
        if(isValido){
            mudarTela(CapturaDadosCalendarFornActivity.class);
        }
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
    private void editarEventoOficialNoServ(String json) throws InterruptedException{
        callServer("PUT",json);
    }
    private void excluirEventoOficialNoServ(String json) throws InterruptedException{
        callServer("DELETE",json);
    }

    private void callServer(final String method, final String data) throws InterruptedException{
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if (method.equals("PUT")){
                    validar = ConectarServidor.putJadiel("https://makepartyserver.herokuapp.com/events", data);
                    Log.i("Script", "OLHAAA: "+ validar);
                    if (validar.substring(2, 5).equals("err")){
                        // Não sei qual o erro
                        validar = "Não foi possível editar o evento";
                        // Rever a mensagem
                    }else{
                        validar = "Anúncio editado com sucesso";
                        isValido = true;
                    }
                }else{
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
            }
        });
        thread.start();
        thread.join();
    }
    public void msgToast(String msgToast){
        Toast.makeText(this, msgToast, Toast.LENGTH_SHORT).show();
        finish();
    }
    public void exibirMsgSeValidouAtualizacaoEvento(){
        Toast.makeText(getApplicationContext(), validar, Toast.LENGTH_SHORT).show();
    }

    public void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }

}
