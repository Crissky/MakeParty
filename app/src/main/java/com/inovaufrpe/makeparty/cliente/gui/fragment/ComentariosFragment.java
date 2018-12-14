package com.inovaufrpe.makeparty.cliente.gui.fragment;

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

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.dominio.Rating;
import com.inovaufrpe.makeparty.cliente.gui.adapter.ComentarioDoAnuncioAdapter;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.fragment.BaseFragment;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.task.TaskListener;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.utils.AndroidUtils;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.user.gui.event.BusEvent;
import com.inovaufrpe.makeparty.user.servico.AnuncioEmComumService;
import com.squareup.otto.Subscribe;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class ComentariosFragment extends BaseFragment {

    private RecyclerView recyclerView;
    private List<Rating> comentarios;
    private String tipo;
    private SwipeRefreshLayout swipeLayout;

    // Action Bar de Contexto
    private ActionMode actionMode;

    // Método para instanciar esse fragment pelo tipo.
    public static ComentariosFragment newInstance(String tipo) {
        Bundle args = new Bundle();
        args.putString("tipo", tipo);
        ComentariosFragment f = new ComentariosFragment();
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
        View view = inflater.inflate(R.layout.fragment_comentarios_list, container, false);

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

        taskComentarios(false);
    }

    // Task para buscar os ads
    private class GetComentariosTask implements TaskListener<List<Rating>> {
        private String nome;

        public GetComentariosTask(String nome) {
            this.nome = nome;
        }

        @Override
        public List<Rating> execute() throws Exception {
            Log.d("Olhaa quem logou",SessaoApplication.getInstance().getTipoDeUserLogado());
            // Busca os comentarios em background
            return AnuncioEmComumService.getComentariosDoAnuncioSelecionado(FiltroAnuncioSelecionado.instance.getAnuncioSelecionado().get_id());
        }

        @Override
        public void updateView(List<Rating> comentarios) {
            if (comentarios != null) {
                ComentariosFragment.this.comentarios = comentarios;

                // O correto seria validar se excluiu e recarregar a lista.
                taskComentarios(true);

                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new ComentarioDoAnuncioAdapter(getContext(), comentarios, onClickComentario()));
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

    protected ComentarioDoAnuncioAdapter.ComentarioDoAnuncioOnClickListener onClickComentario() {
        return new ComentarioDoAnuncioAdapter.ComentarioDoAnuncioOnClickListener() {
            @Override
            public void onClickComentario(ComentarioDoAnuncioAdapter.ComentariosDoAnuncioViewHolder holder, int idx) {
                Rating c = comentarios.get(idx);

                if (actionMode == null) {
                } else {
                }
            }
        };
    }

   /* @Subscribe
    public void onBusAtualizarListacomentarios(BusEvent.NovoComentarioEvent ev) {
        Log.d(TAG,"add: " + ev);
        // Recebeu o evento, atualiza a lista.
        taskcomentarios(false);
    }*/

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                taskComentarios(true);
            }
        };
    }

    private void taskComentarios(boolean pullToRefresh) {
        // Atualiza ao fazer o gesto Swipe To Refresh
        if (AndroidUtils.isNetworkAvailable(getContext())) {
            startTask("comentarios", new ComentariosFragment.GetComentariosTask(null), pullToRefresh ? R.id.swipeToRefresh : R.id.progress);
        } else {
            alert(R.string.msg_error_conexao_indisponivel);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancela o recebimento de eventos.
        SessaoApplication.getInstance().getBus().unregister(this);


    }
}