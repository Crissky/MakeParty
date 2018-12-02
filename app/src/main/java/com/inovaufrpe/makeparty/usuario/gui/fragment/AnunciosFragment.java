package com.inovaufrpe.makeparty.usuario.gui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.infra.ConectarServidor;
import com.inovaufrpe.makeparty.infra.Response;
import com.inovaufrpe.makeparty.usuario.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;
import com.inovaufrpe.makeparty.cliente.gui.DetalhesAnuncioActivity;
import com.inovaufrpe.makeparty.usuario.gui.adapter.AnuncioAdapter;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.usuario.gui.event.BusEvent;
import com.inovaufrpe.makeparty.usuario.servico.AnuncioEmComumService;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.fragment.BaseFragment;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.task.TaskListener;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.utils.AndroidUtils;
import com.squareup.otto.Subscribe;

import org.json.JSONException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ricardo on 12/06/15.
 */
public class AnunciosFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<Ads> ads;
    private String tipo;
    private SwipeRefreshLayout swipeLayout;

    // Action Bar de Contexto
    private ActionMode actionMode;

    // Método para instanciar esse fragment pelo tipo.
    public static AnunciosFragment newInstance(String tipo) {
        Bundle args = new Bundle();
        args.putString("tipo", tipo);
        AnunciosFragment f = new AnunciosFragment();
        f.setArguments(args);
        return f;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.tipo = getArguments().getString("tipo");
        }
        //setHasOptionsMenu(true);

        // Registra a classe para receber eventos.
        SessaoApplication.getInstance().getBus().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anuncios_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        // Swipe to Refresh
        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh);
        swipeLayout.setOnRefreshListener(OnRefreshListener());
        swipeLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        taskAnuncios(false);
    }

    // Task para buscar os ads
    private class GetAnunciosTask implements TaskListener<List<Ads>> {
        private String nome;

        public GetAnunciosTask(String nome) {
            this.nome = nome;
        }

        @Override
        public List<Ads> execute() throws Exception {
            // Busca os Anuncios em background
            if (nome != null) {
                // É uma busca por nome
                //return AnuncioEmComumService.searchByNome(nome);
                return AnuncioEmComumService.getAnunciosByTipo(tipo);
            } else {
                // É para listar por tipo
                //return Retrofit.getAnuncioREST().getAnuncios(tipo);
                return AnuncioEmComumService.getAnunciosByTipo(tipo);
            }
        }

        @Override
        public void updateView(List<Ads> ads) {
            if (ads != null) {
                AnunciosFragment.this.ads = ads;

                // O correto seria validar se excluiu e recarregar a lista.
            taskAnuncios(true);

                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new AnuncioAdapter(getContext(), ads, onClickAnuncio()));
            }
        }

        @Override
        public void onError(Exception e) {
            if(e instanceof SocketTimeoutException) {
                alert(getString(R.string.msg_erro_io_timeout));
            } else {
                alert(getString(R.string.msg_error_io));
            }
        }

        @Override
        public void onCancelled(String s) {

        }
    }

    protected AnuncioAdapter.AnuncioOnClickListener onClickAnuncio() {
        return new AnuncioAdapter.AnuncioOnClickListener() {
            @Override
            public void onClickAnuncio(AnuncioAdapter.AnunciosViewHolder holder, int idx) {
                Ads c = ads.get(idx);

                if (actionMode == null) {
                    ImageView img = holder.img;

                    Intent intent = new Intent(getActivity(), DetalhesAnuncioActivity.class);
                    intent.putExtra("anuncio", c);
                    String key = getString(R.string.transition_key);

                    // Compat
                    ActivityOptionsCompat opts = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), img, key);
                    ActivityCompat.startActivity(getActivity(), intent, opts.toBundle());
                } else {
                    // Seleciona o anuncio e atualiza a lista
                    c.selected = !c.selected;
                    updateActionModeTitle();
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onLongClickAnuncio(AnuncioAdapter.AnunciosViewHolder holder, int idx) {
                if (actionMode != null) {
                    return;
                }

                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                actionMode = getAppCompatActivity().startSupportActionMode(getActionModeCallback());

                Ads c = ads.get(idx);
                c.selected = true;
                recyclerView.getAdapter().notifyDataSetChanged();

                updateActionModeTitle();
            }
        };
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);

        // SearchView
        MenuItem item = menu.findItem(R.id.action_search);
        //SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        //searchView.setOnQueryTextListener(onSearch());
    }

    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //buscaanuncios(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            toast("Faça a busca");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void onBusAtualizarListaAnuncios(BusEvent.NovoAnuncioEvent ev) {
        Log.d(TAG,"add: " + ev);
        // Recebeu o evento, atualiza a lista.
        taskAnuncios(false);
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                taskAnuncios(true);
            }
        };
    }

    private void taskAnuncios(boolean pullToRefresh) {
        // Atualiza ao fazer o gesto Swipe To Refresh
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            startTask("ads", new GetAnunciosTask(null), pullToRefresh ? R.id.swipeToRefresh : R.id.progress);
        } else {
            alert(R.string.msg_error_conexao_indisponivel);
        }
    }

    private void buscaAnuncios(String nome) {
        // Atualiza ao fazer o gesto Swipe To Refresh
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            startTask("ads", new GetAnunciosTask(nome), R.id.progress);
        } else {
            alert(R.string.msg_error_conexao_indisponivel);
        }
    }

    private ActionMode.Callback getActionModeCallback() {
        return new ActionMode.Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Inflate a menu resource providing context menu items
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_selecao, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                List<Ads> selectedAds = getSelectedAnuncios();
                if (item.getItemId()==R.id.action_add_lista_desejo_finalm){
                    SimOuNaoDialog.show(getFragmentManager(),"Deseja adicionar esses anúncios que foram selecionados a sua lista de desejos?", new SimOuNaoDialog.Callback() {
                        @Override
                        public void metodoSimAoDialog() {

                        }
                    });
                }
                /* if (item.getItemId() == R.id.action_remove) {
                    deletarAnunciosSelecionados();
                } else if (item.getItemId() == R.id.action_share) {
                    toast("Compartilhar: " + selectedAds);
                }*/
                // Encerra o action mode
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Limpa o ActionMode e ads selecionados
                actionMode = null;
                for (Ads c : ads) {
                    c.selected = false;
                }
                // Atualiza a lista
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        };
    }

    // Deletar ads selecionados ao abrir a CAB
    private void deletarAnunciosSelecionados() {
        final List<Ads> selectedAds = getSelectedAnuncios();

        if(selectedAds.size() > 0) {
            startTask("deletar",new BaseTask(){
                @Override
                public Object execute() throws Exception {
                    boolean ok = AnuncioEmComumService.deleteItensLista(selectedAds);
                    if(ok) {
                        // Se excluiu do banco, remove da lista da tela.
                        for (Ads c : selectedAds) {
                            ads.remove(c);
                        }
                    }
                    return null;
                }

                @Override
                public void updateView(Object count) {
                    super.updateView(count);
                    // Mostra mensagem de sucesso
                    snack(recyclerView, selectedAds.size() + " ads excluídos com sucesso");
                    // Atualiza a lista de ads
                    taskAnuncios(true);
                    // Atualiza a lista
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            });
        }
    }

    private List<Ads> getSelectedAnuncios() {
        List<Ads> list = new ArrayList<Ads>();
        for (Ads c : ads) {
            if (c.selected) {
                list.add(c);
            }
        }
        return list;
    }

    private void updateActionModeTitle() {
        if (actionMode != null) {
            actionMode.setTitle("Selecione os ads.");
            actionMode.setSubtitle(null);
            List<Ads> selectedAds = getSelectedAnuncios();
            if (selectedAds.size() == 0) {
                actionMode.finish();
            } else if (selectedAds.size() == 1) {
                actionMode.setSubtitle("1 Ads selecionado");
            } else if (selectedAds.size() > 1) {
                actionMode.setSubtitle(selectedAds.size() + " ads selecionados");
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancela o recebimento de eventos.
        SessaoApplication.getInstance().getBus().unregister(this);
    }

}