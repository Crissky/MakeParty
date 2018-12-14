package com.inovaufrpe.makeparty.fornecedor.gui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.fornecedor.dominio.Event;
import com.inovaufrpe.makeparty.fornecedor.gui.DetalhesEventoFornActivity;
import com.inovaufrpe.makeparty.fornecedor.gui.adapter.EventFornAdapter;
import com.inovaufrpe.makeparty.fornecedor.gui.adapter.FiltroEventoSelecionado;
import com.inovaufrpe.makeparty.fornecedor.servico.FornecedorService;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.fragment.BaseFragment;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.task.TaskListener;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.utils.AndroidUtils;
import com.inovaufrpe.makeparty.user.gui.adapter.AnuncioAdapter;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.user.gui.event.BusEvent;
import com.inovaufrpe.makeparty.user.gui.fragment.AnunciosOutroFragment;
import com.inovaufrpe.makeparty.user.servico.AnuncioEmComumService;
import com.squareup.otto.Subscribe;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;


public class EventsFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<Event> events;
    private String tipo;
    private SwipeRefreshLayout swipeLayout;
    private Intent shareIntent;

    // Action Bar de Contexto
    private ActionMode actionMode;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setHasOptionsMenu(true);

        // Registra a classe para receber eventos.
        SessaoApplication.getInstance().getBus().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events_list, container, false);

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

        taskEventsForn(false);
    }

    // Task para buscar os ads
    private class GetEventsFornTask implements TaskListener<List> {
        private String nome;

        public GetEventsFornTask(String nome) {
            this.nome = nome;
        }

        @Override
        public List execute() throws Exception {
            Log.d("Olhaa quem logou",SessaoApplication.getInstance().getTipoDeUserLogado());
            // Busca os Anuncios em background
            if (nome != null) {
                // É uma busca por nome
                //return AnuncioEmComumService.searchByNome(nome);
               // return AnuncioEmComumService.getAnunciosByTipo(tipo);
            } else {
                // É para listar por tipo
                //return Retrofit.getAnuncioREST().getAnuncios(tipo);
                return FornecedorService.getEventosDeUmFornecedor(SessaoApplication.getInstance().getTokenUser());
            }
            return FornecedorService.getEventosDeUmFornecedor(SessaoApplication.getInstance().getTokenUser());
        }

        @Override
        public void updateView(List events) {
            if (events!= null) {
                EventsFragment.this.events = events;
                // O correto seria validar se excluiu e recarregar a lista.
                taskEventsForn(true);
                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new EventFornAdapter(getContext(), events, onClickEventForn()));

            }
        }

        @Override
        public void onError(Exception e) {
            if(e instanceof SocketTimeoutException) {
                alert(getString(R.string.msg_erro_io_timeout));
            } else {
                //alert(getString(R.string.msg_error_io));
            }
        }

        @Override
        public void onCancelled(String s) {

        }
    }

    protected EventFornAdapter.EventFornOnClickListener onClickEventForn() {
        return new EventFornAdapter.EventFornOnClickListener() {

            @Override
            public void onClickEventForn(EventFornAdapter.EventsFornViewHolder holder, int idx) {
                Event dk = events.get(idx);

                if (actionMode == null) {
                    FiltroEventoSelecionado.instance.setEventoSelecionado(dk);
                    Intent intent = new Intent(getActivity(), DetalhesEventoFornActivity.class);
                    String key = getString(R.string.transition_key);
                    startActivity(intent);

                    // Compat
                } else {
                    // Seleciona o anuncio e atualiza a lista
                    dk.selected = !dk.selected;
                    updateActionModeTitle();
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onLongClickEventForn(EventFornAdapter.EventsFornViewHolder holder, int idx) {
                if (actionMode != null) {
                    return;
                }

                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                actionMode = getAppCompatActivity().startSupportActionMode(getActionModeCallback());

                Event dk = events.get(idx);
                dk.selected = true;
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
    public void onBusAtualizarListaEventos(BusEvent.NovoAnuncioEvent ev) {
        Log.d(TAG,"add: " + ev);
        // Recebeu o evento, atualiza a lista.
        taskEventsForn(false);
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                taskEventsForn(true);
            }
        };
    }

    private void taskEventsForn(boolean pullToRefresh) {
        // Atualiza ao fazer o gesto Swipe To Refresh
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            startTask("events", new GetEventsFornTask(null), pullToRefresh ? R.id.swipeToRefresh : R.id.progress);
        } else {
            alert(R.string.msg_error_conexao_indisponivel);
        }
    }

    private void buscaEventos(String nome) {
        // Atualiza ao fazer o gesto Swipe To Refresh
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            startTask("events", new GetEventsFornTask(nome), R.id.progress);
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
                final List<Event> selectedEventsForn = getSelectedEventsForn();
                if (item.getItemId()==R.id.action_delete_item_lista_finalm) {
                    SimOuNaoDialog.show(getFragmentManager(), "Deseja remover esses eventos que foram selecionados da sua lista?", new SimOuNaoDialog.Callback() {
                        @Override
                        public void metodoSimAoDialog() {
                            Log.i("opaaa a lista aq",selectedEventsForn.toString());
                            startTask("events", new EventsFragment.PostOuDeleteTask(selectedEventsForn));
                        }
                    });
                /*} else if (item.getItemId() == R.id.action_share) {
                    toast("Compartilhar: " + selectedEventsForn);
                }
                */
                }
                // Encerra o action mode
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Limpa o ActionMode e ads selecionados
                actionMode = null;
                for (Event c : events) {
                    c.selected = false;
                }
                // Atualiza a lista
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        };
    }

    //Deletar ads selecionados ao abrir a CAB
    private void deletarAnunciosSelecionados() {
        final List<Event> selectedEvents = getSelectedEventsForn();

        if(selectedEvents.size() > 0) {
            startTask("deletar",new BaseTask(){
                @Override
                public Object execute() throws Exception {
                    boolean ok = FornecedorService.deleteItensListaEventosFornecedor(selectedEvents,SessaoApplication.getInstance().getTokenUser());
                    if(ok) {
                        // Se excluiu do banco, remove da lista da tela.
                        for (Event c : selectedEvents) {
                            events.remove(c);
                        }
                    }
                    return null;
                }

                @Override
                public void updateView(Object count) {
                    super.updateView(count);
                    // Mostra mensagem de sucesso
                    snack(recyclerView, selectedEvents.size() + " eventos excluídos com sucesso");
                    // Atualiza a lista de ads
                    taskEventsForn(true);
                    // Atualiza a lista
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            });
        }
    }

    private List<Event> getSelectedEventsForn() {
        List<Event> list = new ArrayList<Event>();
        for (Event c : events) {
            if (c.selected) {
                list.add(c);
            }
        }
        return list;
    }

    private void updateActionModeTitle() {
        if (actionMode != null) {
            actionMode.setTitle("Selecione os eventos.");
            actionMode.setSubtitle(null);
            List<Event> selectedEventsForn = getSelectedEventsForn();
            if (selectedEventsForn.size() == 0) {
                actionMode.finish();
            } else if (selectedEventsForn.size() == 1) {
                actionMode.setSubtitle("1 evento selecionado");
            } else if (selectedEventsForn.size() > 1) {
                actionMode.setSubtitle(selectedEventsForn.size() + " eventos selecionados");
            }
        }
    }
    private class PostOuDeleteTask implements TaskListener<List> {
        //private final List<Event> selectedEvents;
        private String tipoReq="";

        public PostOuDeleteTask(List<Event> selectedEvents) {
            EventsFragment.this.events =getSelectedEventsForn();
        }

        @Override
        public List execute() throws Exception {
            if (getSelectedEventsForn() != null) {
                FornecedorService.deleteItensListaEventosFornecedor(getSelectedEventsForn(),SessaoApplication.getInstance().getTokenUser());
                snack(recyclerView, "Anúncios excluídos da lista com sucesso.");
            }
            return FornecedorService.getEventosDeUmFornecedor(SessaoApplication.getInstance().getTokenUser());
            // Busca os eventos em background (Thread)
        }
        @Override
        public void updateView(List events) {
            if (events != null) {
                // Salva a lista de eventos no atributo da classe
                EventsFragment.this.events = events;
                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new EventFornAdapter(getContext(), events, onClickEventForn()));
            }
        }

        @Override
        public void onError(Exception e) {
            // Qualquer exceção lançada no método execute vai cair aqui.
            if (e instanceof SocketTimeoutException) {
                alert(getString(R.string.msg_erro_io_timeout));
            } else {
                alert(getString(R.string.msg_error_io));
            }
        }

        @Override
        public void onCancelled(String s) {
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancela o recebimento de eventos.
        SessaoApplication.getInstance().getBus().unregister(this);
    }

}