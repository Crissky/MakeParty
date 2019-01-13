package com.inovaufrpe.makeparty.cliente.gui;

import android.content.Context;
import android.content.Intent;
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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inovaufrpe.makeparty.R;
//import com.inovaufrpe.makeparty.usuario.gui.fragment.AnunciosFragment;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.user.gui.fragment.AnunciosOutroFragment;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.gui.EntrarOuCadastrarActivity;

public class TelaInicialClienteActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private BottomNavigationView bottomNavigationView;
    private CoordinatorLayout coordinatorLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial_cliente);
        SessaoApplication.getInstance().setTelaAtual(TelaInicialClienteActivity.class);
        acoesReferentesAoBottomNavigation();
        toolbarComMenuNavAbreEFecha();
        viewDoMenuNavListaClicavel();
        criarFragment(savedInstanceState);


    }
    private void toolbarComMenuNavAbreEFecha(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
        if (savedInstanceState == null) {
            AnunciosOutroFragment frag = new AnunciosOutroFragment();
            //AnunciosFragment frag = new AnunciosFragment();
            frag.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ImageView fotoUsuario;
        TextView nomeUsuario, email;
        fotoUsuario =findViewById(R.id.imageViewNavHeader);
        nomeUsuario = findViewById(R.id.nomeTextNavHeader);
        email = findViewById(R.id.textViewEmailNavHeader);
        if (SessaoApplication.instance.getTipoDeUserLogado().equals("customer")) {
            nomeUsuario.setText(SessaoApplication.instance.getObjCustomerSeEleForTipoLogado().getName());
            email.setText(SessaoApplication.instance.getObjCustomerSeEleForTipoLogado().getUser().getEmail());
        }else if(SessaoApplication.getInstance().getTipoDeUserLogado().equals("advertiser")){
            nomeUsuario.setText(SessaoApplication.instance.getObjOwnerSeEleForTipoLogado().getSocialname());
            email.setText(SessaoApplication.instance.getObjOwnerSeEleForTipoLogado().getUser().getEmail());
        }else{
            nomeUsuario.setText("Cliente sem login");
            email.setText("clientesemlogin@makeparty.com");
        }

        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AnunciosOutroFragment fragment = (AnunciosOutroFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                fragment.search(query.toLowerCase());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {

            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                AnunciosOutroFragment fragment = (AnunciosOutroFragment) getSupportFragmentManager().findFragmentById(R.id.container);
                fragment.closeSearch();
            }
        });

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
            //Share text:
            Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND).setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT, "Conheça o MakeParty e monte sua festa! Link : https://sites.google.com/s/1C1pE2cz49wasqvC1-bJ_CTzQ3wHzIIST/p/1_7go55opFtDbwijzmpPFD6ZTT0g89jrd/edit" );
            startActivity(Intent.createChooser(intent2, "Share via"));

        } else if (id == R.id.nav_exit) {
            SessaoApplication.instance.onTerminate();
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
                    //Por algum motivo o oncreate n pega o bundle(fica dando nul), mas o newinstance ele reconhece sim
                    trocarFragmento("pacotes");
                    //tive q fazer:
                    //tbm como n tem pacote no momento, pq é recomendação , mudei p retornar so festa de vez de pacote
                    FiltroAnuncioSelecionado.instance.setTipoListaPraMostrarSubCategoriaBottomNavCliente("festa");
                    return true;

                }else if (item.getItemId()==R.id.action_casa_festa){
                    trocarFragmento("casa de festa");
                    FiltroAnuncioSelecionado.instance.setTipoListaPraMostrarSubCategoriaBottomNavCliente("casa de festa");
                    return true;
                }else if (item.getItemId()==R.id.action_buffet){
                    trocarFragmento("buffet");
                    FiltroAnuncioSelecionado.instance.setTipoListaPraMostrarSubCategoriaBottomNavCliente("buffet");
                    return true;
                }else if (item.getItemId()==R.id.action_decoracao){
                    trocarFragmento("decoracao");
                    FiltroAnuncioSelecionado.instance.setTipoListaPraMostrarSubCategoriaBottomNavCliente("decoracao");
                    return true;
                }else if(item.getItemId()==R.id.action_animacao){
                    trocarFragmento("animacao");
                    FiltroAnuncioSelecionado.instance.setTipoListaPraMostrarSubCategoriaBottomNavCliente("animacao");
                    return true;
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
        try {
            toolbar.collapseActionView();
            toolbarComMenuNavAbreEFecha();
        }catch (Error error){
            error.printStackTrace();
        }
    }

    private void mudarTela(Class tela){
        Intent intent=new Intent(this, tela);
        startActivity(intent);
        finish();
    }
    public void irParaTelaConfiguracoesCliente(){
        this.mudarTela(ConfigClienteActivity.class);
    }
    public void exibirMsgEAgirSeQuiserSairRealmente(){
        if(SessaoApplication.getInstance().getTipoDeUserLogado().equals("customer")) {
            SimOuNaoDialog.show(getSupportFragmentManager(), "Deseja realmente sair da sua conta ?", new SimOuNaoDialog.Callback() {
                @Override
                public void metodoSimAoDialog() {
                    SessaoApplication.getInstance().onTerminate();
                    mudarTela(EntrarOuCadastrarActivity.class);
                }
            });
        }else{
            mudarTela(EntrarOuCadastrarActivity.class);
        }
    }
    public void irParaTelaListaDeDesejos(){
        this.mudarTela(ListaDesejosClienteActivity.class);
    }
    public void irParaTelaEntrarOuCadastrar(){
        this.mudarTela(EntrarOuCadastrarActivity.class);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            exibirMsgEAgirSeQuiserSairRealmente();
        }
    }
}
