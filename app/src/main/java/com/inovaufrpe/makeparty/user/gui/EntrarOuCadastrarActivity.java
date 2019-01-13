package com.inovaufrpe.makeparty.user.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.TelaInicialClienteActivity;
import com.inovaufrpe.makeparty.fornecedor.gui.TelaInicialFornecedorActivity;
import com.inovaufrpe.makeparty.infra.SessaoApplication;

public class EntrarOuCadastrarActivity extends AppCompatActivity {
    ViewFlipper v_flipper;
    TextView textBtnEntrarSemLogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrar_ou_cadastrar);
        bloqueandoTelaClienteParaFornecedorELoginNovoClienteSemSair();
        encontrandoItensView();
    }
    private void bloqueandoTelaClienteParaFornecedorELoginNovoClienteSemSair(){
        if (SessaoApplication.getInstance().getTipoDeUserLogado().equals("advertiser")){
            this.mudarTela(TelaInicialFornecedorActivity.class);
        }else if(SessaoApplication.getInstance().getTipoDeUserLogado().equals("customer")){
            this.mudarTela(TelaInicialClienteActivity.class);
        }
    }

    private void encontrandoItensView(){
        v_flipper = findViewById(R.id.slideLoginOuCadastroViewFlipper);
        textBtnEntrarSemLogar =findViewById(R.id.btnEntrarSemlogarCliente);
        setUpFlipperGaleriaFotos();
        acoesButton();

    }

    private void acoesButton(){ //So coloquei um aq o resto ta no xml
        textBtnEntrarSemLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irParaTelaClienteSemLogar();
            }
        });
    }
    public void flipperImages(int image){
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;


        ImageView imageView = new ImageView(this);
        imageView.setImageResource(image);
        //abaixo o unico q funcionou para deixar a imagem em tela toda(no pixel A) - era bom colocar conforme o tam de cd
        //tela
        imageView.setScaleX(2);
        imageView.setScaleY((float) 2.55);

        //imageView.getLayoutParams().width = width / getResources().getDisplayMetrics().density;
        //imageView.getLayoutParams().height = pixelsValue / getResources().getDisplayMetrics().density;

        //codigo abaixo n func
        /*CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(800,height);
        imageView.setLayoutParams(layoutParams);
        */
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3000);//3sec de intervalo para mudar de foto
        v_flipper.setAutoStart(true);
        //animation
        v_flipper.setInAnimation(this,android.R.anim.fade_in);
        v_flipper.setOutAnimation(this,android.R.anim.fade_out);
    }
    private void setUpFlipperGaleriaFotos() {
        //Ainda falta add mais foto e tbm refazer de modo q o escuro fiq ok
        int[] images = new int[] {R.drawable.backgroundfestainfchacaraescuro,R.drawable.backgroundclownandkidsescuro,
                R.drawable.backgroundbrinqinflavelescuro,R.drawable.background_flores_lindas_decoracao_festa_infantil_conto_de_fadas_1_800x500};

        v_flipper = findViewById(R.id.slideLoginOuCadastroViewFlipper);

        for (int image:images){
            flipperImages(image);
        }
    }

    private String[] getScreenDimension(){
        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double)width / (double)dens;
        double hi = (double)height / (double)dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        double screenInches = Math.sqrt(x+y);

        String[] screenInformation = new String[3];
        screenInformation[0] = String.valueOf(width) + " px";
        screenInformation[1] = String.valueOf(height) + " px" ;
        screenInformation[2] = String.format("%.2f", screenInches) + " inches" ;

        String[] screenData = getScreenDimension();
        Log.d("widthInPixel", screenData[0]);
        Log.d("heightInPixel", screenData[1]);
        Log.d("inches", screenData[2]);

        return screenInformation;
    }

    private void mudarTela(Class proximaTela){
        Intent intent = new Intent( EntrarOuCadastrarActivity.this, proximaTela);
        startActivity(intent);
        finish();
    }

    public void irParaTelaEntrar(View view) {
        this.mudarTela(LoginActivity.class);
    }

    public void irParaTelaCriarConta(View view) {
        this.mudarTela(CadastroActivity.class);
    }

    public void irParaTelaClienteSemLogar(){
        this.mudarTela(TelaInicialClienteActivity.class);
    }
    @Override
    public void onBackPressed() {
        this.mudarTela(EntrarOuCadastrarActivity.class);
    }
}
