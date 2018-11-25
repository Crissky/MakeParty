package com.inovaufrpe.makeparty.cliente.gui;

import android.app.DatePickerDialog;
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

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.fragment.dialog.CalendarioDialog;

import java.text.DateFormat;
import java.util.Calendar;

public class DetalhesAnuncioActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private ViewPager galeriaPhotos;
    private TextView titleAds,nomeFornecedor,adsMediaGeral,descriptionAds,avaliacaoAds,phoneAds;
    private TextView priceAds, adsTags, addressAds;
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
        //setarInfoView();

        // botão da tela detalhes para acionar o calendario
        botaoDispAds.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CalendarioDialog datePicker = new CalendarioDialog();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
    }


    //IMPLEMENTAÇÃO RELACIONADO AO CALENDÁRIO
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c  = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        TextView textView = (TextView) findViewById(R.id.textView1);
        textView.setText(currentDateString);
    }

    public void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
    }

    public void encontrandoItensViews(){
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


    }

    public void setarInfoView(){

    }

    public void onClickAddDesejo(View view){

    }

    public void avaliarAnuncio(){

    }


}
