package com.inovaufrpe.makeparty.cliente.gui;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.inovaufrpe.makeparty.cliente.dominio.Problem;
import com.inovaufrpe.makeparty.cliente.dominio.Solution;
import com.inovaufrpe.makeparty.cliente.servico.Knapsack;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.task.TaskListener;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.user.servico.AnuncioEmComumService;

import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeekBarsRecomendacaoActivity extends AppCompatActivity {
    private EditText valorCliente;
    private SeekBar seekBarCasa, seekBarBuffet, seekBarDecoracao, seekBarAnimacao;
    private TextView tvCasa, tvBuffet, tvDecoracao, tvAnimacao;
    private Button btProcurar;
    private int valueCasa, valueBuffet, valueDecoracao, valueAnimacao, valorMax, somaValues;
    private List<Ad> adList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Problem problem;
    private Map<String, Integer> codeGroup = new HashMap<>();
    private List<Ad> selectedAds = new ArrayList<>();

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
        }else {
            valorMax = Math.round(Float.parseFloat(temp));
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
            somaValues = valueCasa + valueBuffet + valueDecoracao + valueAnimacao;
        }
        return ok;
    }

    protected void alert(String msg) {
        Toast.makeText(SeekBarsRecomendacaoActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void doGetRecomendacao(){
        new getRecomendacao().execute();
    }

    private void taskAnuncios() {
        new getAnuncios().execute();
    }

    private class getAnuncios extends AsyncTask{

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(SeekBarsRecomendacaoActivity.this);
            progressDialog.setMessage("Carregando");
            progressDialog.show();
            adList.clear();
            selectedAds.clear();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            List<Ad> ads = new ArrayList<>();
            List<Ad> temp = new ArrayList<>();
            int i = 0;
            try {
                if (valueCasa > 0){
                    temp = AnuncioEmComumService.getAnunciosByTipo("casa de festa");
                    for (int e = 0; e < temp.size(); e++){
                        if (temp.get(e).getPrice() < 0 || temp.get(e).get_id().equals("5c0e19e4b389200016261cf5")){
                            temp.remove(e);
                        }
                    }
                    ads.addAll(temp);
                    if (temp.size() > 0){
                        codeGroup.put("Casa de Festa", i);
                        i++;
                        temp.clear();
                    }
                }
                if (valueBuffet > 0){
                    temp = AnuncioEmComumService.getAnunciosByTipo("buffet");
                    for (int e = 0; e < temp.size(); e++){
                        if (temp.get(e).getPrice() < 0 || temp.get(e).get_id().equals("5c0e19e4b389200016261cf5")){
                            temp.remove(e);
                        }
                    }
                    ads.addAll(temp);
                    if (temp.size() > 0){
                        codeGroup.put("Buffet", i);
                        i++;
                        temp.clear();
                    }
                }
                if (valueDecoracao > 0){
                    temp = AnuncioEmComumService.getAnunciosByTipo("decoracao");
                    for (int e = 0; e < temp.size(); e++){
                        if (temp.get(e).getPrice() < 0 || temp.get(e).get_id().equals("5c0e19e4b389200016261cf5")){
                            temp.remove(e);
                        }
                    }
                    ads.addAll(temp);
                    if (temp.size() > 0){
                        codeGroup.put("Decoracao", i);
                        i++;
                        temp.clear();
                    }
                }
                if (valueAnimacao > 0){
                    temp = AnuncioEmComumService.getAnunciosByTipo("animacao");
                    for (int e = 0; e < temp.size(); e++){
                        if (temp.get(e).getPrice() < 0 || temp.get(e).get_id().equals("5c0e19e4b389200016261cf5")){
                            temp.remove(e);
                        }
                    }
                    ads.addAll(temp);
                    if (temp.size() > 0){
                        codeGroup.put("Animacao", i);
                        temp.clear();
                    }
                }


            }catch (Exception e){
                e.printStackTrace();
            }
            adList.addAll(ads);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

            if (adList.size() == 0) {
                alert("Não foi encontrado nenhum anuncio");
                progressDialog.dismiss();
            }else {
                doGetRecomendacao();
            }
        }
    }

    private class getRecomendacao extends AsyncTask{

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            problem = new Problem();
            int bagSize = valorMax;
            int[] profit = new int[adList.size()];
            int[] weight = new int[adList.size()];
            int[] group = new int[adList.size()];
            for (int i = 0; i < adList.size(); i++){
                profit[i] = calcProfit(adList.get(i).getPrice(), adList.get(i).getType());
                weight[i] = (int) Math.round(adList.get(i).getPrice());
                group[i] = codeGroup.get(adList.get(i).getType());
            }
            problem.setBagSize(bagSize);
            problem.setProfit(profit);
            problem.setWeight(weight);
            problem.setGroup(group);
            Knapsack knapsack = new Knapsack(problem);
            Solution solution = knapsack.solve();
            List<Boolean> booleans = solution.getSolution();
            List<Integer> i = new ArrayList<>();
            while (true) {
                int index = booleans.indexOf(true);
                if (index > -1) {
                    i.add(index + i.size());
                    booleans.remove(index);
                }else {
                    break;
                }
            }
            for (int x : i){
                selectedAds.add(adList.get(x));
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            boolean ani = false;
            boolean buf = false;
            boolean cas = false;
            boolean dec = false;
            if (valueAnimacao > 0){
                ani = true;
            }if (valueBuffet > 0){
                buf = true;
            }if (valueCasa > 0){
                cas = true;
            }if (valueDecoracao > 0){
                dec = true;
            }
            for (Ad ad: selectedAds){
                String tipo = ad.getType();
                if (ani && tipo.equals("Animacao")){
                    ani = false;
                }else if (buf && tipo.equals("Buffet")){
                    buf = false;
                }else if (cas && tipo.equals("Casa de Festa")){
                    cas = false;
                }else if (dec && tipo.equals("Decoracao")){
                    dec = false;
                }
            }
            if (ani || buf || cas || dec){
                alert("Não foi possível gerar um pacote");
            }else {
                SessaoApplication.setAdRecomendacao(selectedAds);
                startActivity(new Intent(SeekBarsRecomendacaoActivity.this, ResultadoRecomendacaoActivity.class));
            }
            progressDialog.dismiss();

        }
    }

    private int calcProfit(double valor, String tipo){
        BigDecimal tipoV = new BigDecimal(getValue(tipo));
        BigDecimal totalV = new BigDecimal(somaValues);
        BigDecimal total = new BigDecimal(valorMax);
        BigDecimal vPerPoint = total.divide(totalV, BigDecimal.ROUND_UP);
        BigDecimal vTipo = vPerPoint.multiply(tipoV);
        BigDecimal v = new BigDecimal(valor);
        BigDecimal d = v.subtract(vTipo).abs();
        int leg = d.compareTo(new BigDecimal(1000));
        if (leg == 1){
            d = new BigDecimal(1000);
        }
        BigDecimal c = new BigDecimal(4);
        BigDecimal coe = d.multiply(c);
        BigDecimal res = new BigDecimal(5000).subtract(coe);
        return res.intValue();
    }

    private int getValue(String tipo){
        int v = 0;
        if (tipo.equals("Casa de Festa")){
            v = valueCasa;
        }else if (tipo.equals("Buffet")){
            v = valueBuffet;
        }else if (tipo.equals("Decoracao")){
            v = valueDecoracao;
        }else if (tipo.equals("Animacao")){
            v = valueAnimacao;
        }
        return v;
    }
}
