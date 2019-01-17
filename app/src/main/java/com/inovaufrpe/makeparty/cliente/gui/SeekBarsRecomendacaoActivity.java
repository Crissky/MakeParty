package com.inovaufrpe.makeparty.cliente.gui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.task.TaskListener;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.user.servico.AnuncioEmComumService;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class SeekBarsRecomendacaoActivity extends AppCompatActivity {
    private EditText valorCliente;
    private SeekBar seekBarCasa, seekBarBuffet, seekBarDecoracao, seekBarAnimacao;
    private TextView tvCasa, tvBuffet, tvDecoracao, tvAnimacao;
    private Button btProcurar;
    private int valueCasa, valueBuffet, valueDecoracao, valueAnimacao;
    private List<Ad> adList = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seek_bars_recomendacao);
        setUpToolbar();
        getViews();
    }

    private void getViews(){
        valorCliente = findViewById(R.id.editTextValorRecomendacao);
        tvCasa = findViewById(R.id.textViewCasaRecomendacao);
        tvBuffet = findViewById(R.id.textViewBuffetRecomendacao);
        tvDecoracao = findViewById(R.id.textViewDecoracaoRecomendacao);
        tvAnimacao = findViewById(R.id.textViewAnimacaoRecomendacao);
        seekBarCasa = findViewById(R.id.sbCasaRecomendacao);
        seekBarCasa.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCasa.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarBuffet = findViewById(R.id.sbBuffetRecomendacao);
        seekBarBuffet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvBuffet.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarDecoracao = findViewById(R.id.sbDecoracaoRecomendacao);
        seekBarDecoracao.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDecoracao.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seekBarAnimacao = findViewById(R.id.sbAnimacaoRecomendacao);
        seekBarAnimacao.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvAnimacao.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        btProcurar = findViewById(R.id.buttonProcurarRecomendacao);
        btProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etIsEmptyOrInvalid(valorCliente) && getSBValues()){
                    taskAnuncios();
                }
            }
        });
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


    private boolean etIsEmptyOrInvalid(EditText editText){
        String temp = editText.getText().toString().trim();
        boolean ok = true;
        if (temp.isEmpty() || Float.parseFloat(temp) <= 0){
            alert("Insira um valor válido");
            ok = false;
        }
        return ok;
    }

    private boolean getSBValues(){
        int valueCasaTemp, valueBuffetTemp, valueDecoracaoTemp, valueAnimacaoTemp;
        valueCasaTemp = seekBarCasa.getProgress();
        valueBuffetTemp = seekBarBuffet.getProgress();
        valueDecoracaoTemp = seekBarDecoracao.getProgress();
        valueAnimacaoTemp = seekBarAnimacao.getProgress();
        boolean ok = true;
        if (valueCasaTemp + valueBuffetTemp + valueDecoracaoTemp + valueAnimacaoTemp == 0){
            alert("Todos os tipos foram ignorados");
            ok = false;
        }else {
            valueCasa = valueCasaTemp;
            valueBuffet = valueBuffetTemp;
            valueDecoracao = valueDecoracaoTemp;
            valueAnimacao = valueAnimacaoTemp;
        }
        return ok;
    }

    protected void alert(String msg) {
        Toast.makeText(SeekBarsRecomendacaoActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void generateProblem(){

    }

    private void taskAnuncios() {
        // Busca os carros: Dispara a Task
//        startTask("ads", new GetAnunciosTask(pullToRefresh), pullToRefresh ? R.id.swipeToRefresh : R.id.progress);
        new getAnuncios().execute();
    }

    private class GetAnunciosTask implements TaskListener<List> {
        private boolean refresh;

        public GetAnunciosTask(boolean refresh) {
            this.refresh = refresh;
        }

        @Override
        public List execute() throws Exception {
            Log.d("Olhaa quem logou", SessaoApplication.getInstance().getTipoDeUserLogado());
            //Log.d("tiporetornado",tipo);
            return AnuncioEmComumService.getAnunciosByTipo("buffet");
            // Busca os anuncios em background (Thread)
        }

        @Override
        public void updateView(List ads) {
            if (ads != null) {
                // Salva a lista de anuncios no atributo da classe
                adList.addAll(ads);
                alert("Foi");
            }
        }

        @Override
        public void onError(Exception e) {
            // Qualquer exceção lançada no método execute vai cair aqui.
            if (e instanceof SocketTimeoutException) {
                alert(getString(R.string.msg_erro_io_timeout));
            } else {
                //alert(getString(R.string.msg_error_io));
            }
        }

        @Override
        public void onCancelled(String s) {
        }
    }

    private class getAnuncios extends AsyncTask{

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(SeekBarsRecomendacaoActivity.this);
            progressDialog.setMessage("Carregando");
            progressDialog.show();
            adList.clear();
        }

        @Override
        protected List<Ad> doInBackground(Object[] objects) {
            List<Ad> ads = new ArrayList<>();
            try {
                if (valueCasa > 0){
                    ads.addAll(AnuncioEmComumService.getAnunciosByTipo("casa de festa"));
                }
                if (valueBuffet > 0){
                    ads.addAll(AnuncioEmComumService.getAnunciosByTipo("buffet"));
                }
                if (valueDecoracao > 0){
                    ads.addAll(AnuncioEmComumService.getAnunciosByTipo("decoracao"));
                }
                if (valueAnimacao > 0){
                    ads.addAll(AnuncioEmComumService.getAnunciosByTipo("animacao"));
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            adList.addAll(ads);
            return ads;
        }

        @Override
        protected void onPostExecute(Object o) {
            progressDialog.dismiss();
            if (adList.size() == 0){
                alert("ta vazio");
            }else {
                alert("foi");
            }
        }
    }
}
