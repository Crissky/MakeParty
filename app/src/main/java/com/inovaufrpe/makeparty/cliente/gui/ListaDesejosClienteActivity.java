package com.inovaufrpe.makeparty.cliente.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.dominio.WishListService;
import com.inovaufrpe.makeparty.cliente.dominio.Wishlists;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.gui.fragment.AnunciosOutroFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListaDesejosClienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_desejos_cliente);
        SessaoApplication.getInstance().setTelaAtual(ListaDesejosClienteActivity.class);
        criarFragment(savedInstanceState);
        setUpToolbar();

        //String json =conectarServidorGet(url);
        //Log.d("um json ai", json);
        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(WishListService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        WishListService service = retrofit.create(WishListService.class);
        Call<Wishlists> requestWishlists = service.wishListUser();
        requestWishlists.enqueue(new Callback<Wishlists>() {
            @Override
            public void onResponse(Call<Wishlists> call, retrofit2.Response<Wishlists> response) {
                if (!response.isSuccessful()) {
                    Log.i("RETROFITCODSUF", "Erro:" + response.code());
                } else {
                    //Requisicao retornou com sucesso
                    Wishlists wishlists = response.body();
                    for (Ad df : wishlists.ads) {
                        Log.i("jjRetrofit", String.format("%s:%s", df.get_id(), df.getPrice()));
                    }
                }
            }

            @Override
            public void onFailure(Call<Wishlists> call, Throwable t) {
                Log.i("opaRetrofit", t.getMessage());
            }
        });
        */

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
    private void criarFragment(Bundle savedInstanceState) {
        //getSupportActionBar().setTitle(getString(getIntent().getIntExtra("tipo",6)));
        if (savedInstanceState == null) {
            AnunciosOutroFragment frag = new AnunciosOutroFragment();
            //AnunciosFragment frag = new AnunciosFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.containerFAV, frag).commit();
        }
    }
    public void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        this.mudarTela(TelaInicialClienteActivity.class);

    }
}
