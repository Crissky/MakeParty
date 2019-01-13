package com.inovaufrpe.makeparty.user.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.user.gui.adapter.DetalheAnuncioSlideFotos.GaleriaFotosAdapter;
import com.inovaufrpe.makeparty.user.servico.AnuncioEmComumService;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class NovaTelaEscolhaLoginCadastroActivity extends AppCompatActivity {
    private Context context;
    private ViewPager galeriaPhotos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_tela_escolha_login_cadastro);
        encontrandoItensView();
    }
    private void encontrandoItensView(){
        this.galeriaPhotos = findViewById(R.id.viewPager);
        setUpViewPagerGaleriaFotos();
    }
    private void setUpViewPagerGaleriaFotos() {
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        GaleriaFotosAdapter galeriaFotosAdapter = new GaleriaFotosAdapter(this, getImagensSlideFundo() );
        galeriaPhotos.setAdapter(galeriaFotosAdapter);
        indicator.setViewPager(galeriaPhotos);
    }
    public ArrayList<Bitmap> getImagensSlideFundo(){
        ArrayList<Bitmap> bitMapList = new ArrayList<>();
        int[] mImageIds = new int[] {R.drawable.backgroundkidsandclown,R.drawable.calendarquatro};
        Bitmap drawOne = BitmapFactory.decodeResource(getResources(), R.drawable.backgroundkidsandclown);
        Bitmap drawTwo = BitmapFactory.decodeResource(getResources(), R.drawable.calendarquatro);
        bitMapList.add(drawOne);
        bitMapList.add(drawTwo);

        return bitMapList;
    }

}
