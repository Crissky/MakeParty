package com.inovaufrpe.makeparty.cliente.gui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.MenuItem;

import com.inovaufrpe.makeparty.R;
//import com.inovaufrpe.makeparty.usuario.gui.fragment.AnunciosFragment;
import com.inovaufrpe.makeparty.usuario.gui.LoginActivity;
import com.inovaufrpe.makeparty.usuario.gui.fragment.AnunciosFragment;
import com.inovaufrpe.makeparty.usuario.gui.fragment.AnunciosOutroFragment;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.usuario.gui.EntrarOuCadastrarActivity;

public class TelaInicialClienteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private CoordinatorLayout coordinatorLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_cliente);
        acoesReferentesAoBottomNavigation();
        toolbarComMenuNavAbreEFecha();
        viewDoMenuNavListaClicavel();
        criarFragment(savedInstanceState);
        SessaoApplication.getInstance().setTelaAtual(TelaInicialClienteActivity.class);


    }
    private void toolbarComMenuNavAbreEFecha(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void viewDoMenuNavListaClicavel(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void criarFragment(Bundle savedInstanceState) {
        //getSupportActionBar().setTitle(getString(getIntent().getIntExtra("tipo",6)));
        if (savedInstanceState == null) {
            AnunciosOutroFragment frag = new AnunciosOutroFragment();
            //AnunciosFragment frag = new AnunciosFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!SessaoApplication.instance.getTipoDeUserLogado().equals("null"));{
            //TextView nomeUsuario, email;
            //nomeUsuario = findViewById(R.id.nomeView);
            //email = findViewById(R.id.emailView);
            //nomeUsuario.setText(SessaoApplication.instance.getPessoa().getNome());
            //email.setText(SessaoApplication.instance.getPessoa().getUsuario().getEmail());
        }

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings1) {
            return true;
        } else if (id == R.id.action_search) {
        } else if (id == R.id.action_filtrar_por_regiao) {
        } else if (id == R.id.action_filtrar_por_preco) {
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (SessaoApplication.instance.getTipoDeUserLogado().equals("null")){

        }
        if (id == R.id.nav_camera) {
            // Handle the camera action
            if (!SessaoApplication.instance.getTipoDeUserLogado().equals("null")) {  }else { irParaTelaEntrarOuCadastrar(); }
        }else if (id==R.id.nav_lista_desejos){
            if (!SessaoApplication.instance.getTipoDeUserLogado().equals("null")) { irParaTelaListaDeDesejos(); }else { irParaTelaEntrarOuCadastrar(); }
        }else if (id==R.id.nav_lista_reunioes_tela_cliente){
            if (!SessaoApplication.instance.getTipoDeUserLogado().equals("null")) { }else {irParaTelaEntrarOuCadastrar(); }
        }else if (id==R.id.nav_lista_chat){
            if (!SessaoApplication.instance.getTipoDeUserLogado().equals("null")) { }else {irParaTelaEntrarOuCadastrar(); }
        }
        else if (id == R.id.nav_slideshow) {
        } else if (id == R.id.nav_manage) {
            if (!SessaoApplication.instance.getTipoDeUserLogado().equals("null")) { irParaTelaConfiguracoesCliente(); }else { irParaTelaEntrarOuCadastrar(); }
        } else if (id == R.id.nav_share) {
        } else if (id == R.id.nav_exit) {
            SessaoApplication.instance.onTerminate();
            String qmsetado= SessaoApplication.getInstance().getTipoDeUserLogado();
            mudarTela(EntrarOuCadastrarActivity.class);
            SessaoApplication.getInstance().getTipoDeUserLogado();
            Log.i("Script", "OLHAAA esse era antes" + qmsetado);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                finishAffinity();
            }
            Log.i("Script", "OLHAAA: "+ qmsetado);
            String qmsetadohehe= SessaoApplication.instance.getTipoDeUserLogado();
            Log.i("Script", "OLHAAA: "+ qmsetadohehe);
            //finish();
            //System.exit(0);
            //SessaoApplication.instance.onTerminate();
            Log.i("Script", "OLHAAA: "+ qmsetadohehe);
            //finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected Context getContext() {
        return this;
    }

    public void acoesReferentesAoBottomNavigation(){
        bottomNavigationView =  (BottomNavigationView) findViewById(R.id.tab_bar_opcoes_embaixo);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()  {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.action_pacotes){
                    trocarFragmento("pacotes");
                }else if (item.getItemId()==R.id.action_casa_festa){
                    trocarFragmento("casadefesta");
                }else if (item.getItemId()==R.id.action_buffet){
                    trocarFragmento("buffet");
                }else if (item.getItemId()==R.id.action_decoracao){
                    trocarFragmento("decoracao");
                }else if (item.getItemId()==R.id.action_animacao){
                    trocarFragmento("animacao");
                }
                return false;
            }
        });

    }
    public void trocarFragmento(String tipo){
        Fragment frag = null;
        getSupportActionBar().setTitle(getIntent().getStringExtra(tipo));
        frag =AnunciosOutroFragment.newInstance(tipo);
        frag.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();

    }

    private void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }
    public void irParaTelaConfiguracoesCliente(){
        this.mudarTela(AtualizarPerfilClienteActivity.class);
    }
    public void irParaTelaListaDeDesejos(){
        this.mudarTela(ListaDesejosClienteActivity.class);
    }
    public void irParaTelaEntrarOuCadastrar(){
        this.mudarTela(EntrarOuCadastrarActivity.class);

    }


}
