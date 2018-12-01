package com.inovaufrpe.makeparty.cliente.gui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.adapter.DetalheAnuncioSlideFotos.GaleriaFotosAdapter;
import com.inovaufrpe.makeparty.cliente.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.cliente.gui.fragment.dialog.CalendarioDialog;
import com.inovaufrpe.makeparty.cliente.gui.fragment.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;
import com.inovaufrpe.makeparty.fornecedor.gui.AnunciosFornecedorActivity;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.usuario.gui.EntrarOuCadastrarActivity;

import java.text.DateFormat;
import java.util.Calendar;

import me.relex.circleindicator.CircleIndicator;

public class DetalhesAnuncioActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ViewPager galeriaPhotos;
    private TextView titleAds, nomeFornecedor, adsMediaGeral, descriptionAds, avaliacaoAds, phoneAds, datapub;
    private TextView priceAds, adsTags, addressAds;
    private TextView avaliarAquiText;
    private RatingBar ratingMediaTipoAnuncio;
    private Button botaoDispAds;
    private FloatingActionButton floatingAddListaDesejo;
    private FrameLayout containerComentarios;
    private View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_anuncio);
        encontrandoItensViews();

    }

    //IMPLEMENTAÇÃO RELACIONADO AO CALENDÁRIO
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        FiltroAnuncioSelecionado.instance.setDiaSelecionadoPeloClienteDisp(datePicker.getMaxDate());
        mudarTela(DispDiaSelecPeloClienteActivity.class);

    }

    public void encontrandoItensViews() {
        this.galeriaPhotos = findViewById(R.id.viewPager);
        this.titleAds = findViewById(R.id.textViewTitleAds);
        this.nomeFornecedor = findViewById(R.id.textViewNomeFornecedor);
        this.floatingAddListaDesejo = findViewById(R.id.floatingButtonAddListDesejo);
        this.adsMediaGeral = findViewById(R.id.textViewAnuncioMediaGeral);
        this.ratingMediaTipoAnuncio = findViewById(R.id.ratingBarMediaTipoAnuncioFornecedor);
        this.descriptionAds = findViewById(R.id.textViewDescription);
        this.avaliacaoAds = findViewById(R.id.textViewAvaliacao);
        this.phoneAds = findViewById(R.id.textViewTelefoneAds);
        this.priceAds = findViewById(R.id.textViewPriceAds);
        this.adsTags = findViewById(R.id.textViewTags);
        this.addressAds = findViewById(R.id.textViewAddressAds);
        this.botaoDispAds = findViewById(R.id.buttonVerifDispAnuncio);
        this.containerComentarios = findViewById(R.id.containerComentariosAnuncio);
        this.avaliarAquiText = findViewById(R.id.textViewButtonAvaliar);
        this.datapub = findViewById(R.id.textViewDataPublicacaoAnuncio);
        acoesBotoes();
        setUpViewPagerGaleriaFotos();
        setarInfoView();
    }

    public void acoesBotoes() {
        // botão da tela detalhes para acionar o calendario
        botaoDispAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalendarioDialog datePicker = new CalendarioDialog();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        avaliarAquiText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telaAvaliarAnuncio();
            }
        });
        floatingAddListaDesejo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemAnuncioAListaDesejo();
            }
        });
    }

    private void setUpViewPagerGaleriaFotos() {
        //AnuncioService anuncioService = new AnuncioService(); -- TEM Q ADAPTAR AINDA VIUUUUUUUUU
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        //GaleriaFotosAdapter galeriaFotosAdapter = new GaleriaFotosAdapter(this, AnuncioService.getImagens() ); --ADAPTAR AINDA
        //galeriaPhotos.setAdapter(galeriaFotosAdapter);
        indicator.setViewPager(galeriaPhotos);
    }

    public void setarInfoView() {
        Ads anuncioSelecionado = FiltroAnuncioSelecionado.instance.getAnuncioSelecionado();
        titleAds.setText(anuncioSelecionado.getTitle());
        //nomeFornecedor.setText(("Nome do fornecedor :" +anuncioSelecionado.getOwner().getSocialname()));
        //datapub.setText(anuncioSelecionado.getCreatedAt().toString());
        descriptionAds.setText(("Descrição: " + anuncioSelecionado.getDescription()));
        phoneAds.setText(("Telefone :" + anuncioSelecionado.getPhone()));
        //priceAds.setText((int) anuncioSelecionado.getPrice()); --METODO DE CONV ERRADO
        /*addressAds.setText(("Endereço : "+anuncioSelecionado.getAddress().getStreet()
                +","+ anuncioSelecionado.getAddress().getNeighborhood()+","+ "Número:"
                +anuncioSelecionado.getAddress().getNumber()+ anuncioSelecionado.getAddress().getCity()
                +","+anuncioSelecionado.getAddress().getState()
                +", CEP :"+anuncioSelecionado.getAddress().getZipcode()
        ));
        */
        //adsTags.setText(("Tags : " +anuncioSelecionado.getTags().toString())); ---ERRO NA CONVERSAO METODO TA ERRADO
        setUpViewPagerGaleriaFotos();
        buscarComentariosEAvaliacoes();

    }

    public void buscarComentariosEAvaliacoes() {
    }

    public void addItemAnuncioAListaDesejo() {
        SimOuNaoDialog.show(getSupportFragmentManager(), "Deseja adicionar esse anúncio a sua lista de desejo ?", new SimOuNaoDialog.Callback() {
            @Override
            public void metodoSimAoDialog() {
                //
                //
                //
                if(SessaoApplication.getInstance().getTipoDeUserLogado().equals("null")){
                    mudarTela(EntrarOuCadastrarActivity.class);
                }else if(SessaoApplication.getInstance().getTipoDeUserLogado().equals("customer")){
                    showProgressDialogWithTitle("Por favor, espere", "adicionano esse anúncio a sua lista de desejo");
                    msgToast("Anúncio adicionado a lista de desejos com sucesso");
                    msgToast("Erro"); /////////////ERRRRRRRRRRRRRRRRRRRRRO PQ AINDA N ADD ND, TENHO Q ACRESC OS METOOOOOOODOSS DE ADD
                } else if(SessaoApplication.getInstance().getTipoDeUserLogado().equals("advertiser")){
                    msgToast("Método negado, entre com uma conta do tipo cliente para fazer essa ação");
                }
            }
        });

    }
    public void showProgressDialogWithTitle(String title, String msgEmbaixo) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(title);
        progressDialog.setMessage(msgEmbaixo);
        progressDialog.show();
    }
    public void msgToast(String msgToast){
        Toast.makeText(this, msgToast, Toast.LENGTH_SHORT).show();
        finish();
    }

    public void telaAvaliarAnuncio() {
        mudarTela(AvaliacaoNotaActivity.class);

    }

    public void mudarTela(Class tela) {
        Intent intent = new Intent(this, tela);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        if (SessaoApplication.getInstance().getTipoDeUserLogado().equals("customer")) {
            this.mudarTela(TelaInicialClienteActivity.class);
            //}else if (SessaoApplication.getInstance().getTipoDeUserLogado().equals("customer")){

        } else if (SessaoApplication.getInstance().getTipoDeUserLogado().equals("advertiser")) {
            this.mudarTela(AnunciosFornecedorActivity.class);
        }
    }
}


