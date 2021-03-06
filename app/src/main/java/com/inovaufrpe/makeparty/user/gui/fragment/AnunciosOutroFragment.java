package com.inovaufrpe.makeparty.user.gui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.cliente.gui.DetalhesAnuncioActivity;
import com.inovaufrpe.makeparty.cliente.gui.TelaInicialClienteActivity;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ad;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.utils.IOUtils;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.utils.SDCardUtils;
import com.inovaufrpe.makeparty.user.gui.LoginActivity;
import com.inovaufrpe.makeparty.user.gui.adapter.AnuncioAdapter;
import com.inovaufrpe.makeparty.user.gui.adapter.FiltroAnuncioSelecionado;
import com.inovaufrpe.makeparty.user.gui.dialog.SimOuNaoDialog;
import com.inovaufrpe.makeparty.fornecedor.gui.EditarAnuncioActivity;
import com.inovaufrpe.makeparty.infra.SessaoApplication;
import com.inovaufrpe.makeparty.user.servico.AnuncioEmComumService;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.fragment.BaseFragment;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.task.TaskListener;
import com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid.utils.AndroidUtils;
import com.squareup.otto.Subscribe;

import java.io.File;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class AnunciosOutroFragment extends BaseFragment {
    protected RecyclerView recyclerView;
    private String tipo;
    private List<Ad> ads;
    private SwipeRefreshLayout swipeLayout;
    private ActionMode actionMode;
    private Intent shareIntent;
    private List<Ad> tempAds;

    // Método para instanciar esse fragment pelo tipo.
    public static AnunciosOutroFragment newInstance(String tipo) {
        Bundle args = new Bundle();
        args.putString("tipo", tipo);
        args.putString("kimLinda","kimhahahaha");
        Log.i("tipoOuuu", tipo);
        Log.i("Args éee", String.valueOf(args));
        AnunciosOutroFragment f = new AnunciosOutroFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i("arg no on createeeeeee", String.valueOf(getArguments()));
        super.onCreate(savedInstanceState);
        Log.i("arg no on create", String.valueOf(getArguments()));
        if (getArguments() != null) {
            // Lê o tipo dos argumentos.
            this.tipo = getArguments().getString("tipo");
            Log.i("oxente", getArguments().getString("tipo"));
        } else {
            //POR ENQ AQ TA FESTA VIU, MAS TEM Q MUDARRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR ,
            this.tipo = "festa";
        }
        //setHasOptionsMenu(true); oq é isso? non sei
        // Registra a classe para receber eventos.
        SessaoApplication.getInstance().getBus().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anuncios_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
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
    private class GetAnunciosTask implements TaskListener<List> {
        private boolean refresh;

        public GetAnunciosTask(boolean refresh) {
            this.refresh = refresh;
        }

        @Override
        public List execute() throws Exception {
            Log.d("Olhaa quem logou", SessaoApplication.getInstance().getTipoDeUserLogado());
            //Log.d("tiporetornado",tipo);
            return AnuncioEmComumService.getAnunciosByTipo(FiltroAnuncioSelecionado.instance.getTipoListaPraMostrarSubCategoriaBottomNavCliente());
            // Busca os anuncios em background (Thread)
        }

        @Override
        public void updateView(List ads) {
            if (ads != null) {
                // Salva a lista de anuncios no atributo da classe
                AnunciosOutroFragment.this.ads = ads;
                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new AnuncioAdapter(getContext(), ads, onClickAnuncio()));
            }
        }

        @Override
        public void onError(Exception e) {
            // Qualquer exceção lançada no método execute vai cair aqui.
            if (e instanceof SocketTimeoutException) {
                alert(getString(R.string.msg_erro_io_timeout));
            } else {
                //alert(getString(R.string.msg_error_io));
            }
        }

        @Override
        public void onCancelled(String s) {
        }
    }

    private AnuncioAdapter.AnuncioOnClickListener onClickAnuncio() {
        return new AnuncioAdapter.AnuncioOnClickListener() {
            @Override
            public void onClickAnuncio(AnuncioAdapter.AnunciosViewHolder holder, int indexAnuncio) {
                Ad c = ads.get(indexAnuncio);
                FiltroAnuncioSelecionado.instance.setAnuncioSelecionado(c);
                if (actionMode == null) {
                    if (SessaoApplication.getInstance().getTipoDeUserLogado().equals("advertiser")) {
                        Intent intent = new Intent(getContext(), EditarAnuncioActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getContext(), DetalhesAnuncioActivity.class);
                        startActivity(intent);
                    }
                } else { // Se a CAB está ativada
                    // Seleciona o anuncio
                    c.selected = !c.selected;
                    // Atualiza o título com a quantidade de anuncios selecionados
                    updateActionModeTitle();
                    // Redesenha a lista
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onLongClickAnuncio(AnuncioAdapter.AnunciosViewHolder holder, int indexAnuncio) {
                if (actionMode != null) {
                    return;
                }
                // Liga a action bar de contexto (CAB)
                actionMode = getAppCompatActivity().
                        startSupportActionMode(getActionModeCallback());
                Ad c = ads.get(indexAnuncio);
                c.selected = true; // Seleciona o anuncio
                // Solicita ao Android para desenhar a lista novamente
                recyclerView.getAdapter().notifyDataSetChanged();
                // Atualiza o título para mostrar a quantidade de anuncios selecionados
                updateActionModeTitle();
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancela o recebimento de eventos.
        SessaoApplication.getInstance().getBus().unregister(this);
    }

    @Subscribe
    public void onBusAtualizarListaAnuncios(String refresh) {
        // Recebeu o evento, atualiza a lista.
        taskAnuncios(false);
    }

    private SwipeRefreshLayout.OnRefreshListener OnRefreshListener() {
        return new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Valida se existe conexão ao fazer o gesto Pull to Refresh
                if (AndroidUtils.isNetworkAvailable(getContext())) {
                    // Atualiza ao fazer o gesto Pull to Refresh
                    taskAnuncios(true);
                } else {
                    swipeLayout.setRefreshing(false);
                    snack(recyclerView, R.string.msg_error_conexao_indisponivel);
                }
            }
        };
    }

    private void taskAnuncios(boolean pullToRefresh) {
        // Busca os carros: Dispara a Task
        startTask("ads", new GetAnunciosTask(pullToRefresh), pullToRefresh ? R.id.swipeToRefresh : R.id.progress);
    }

    // Atualiza o título da action bar (CAB)
    private void updateActionModeTitle() {
        if (actionMode != null) {
            actionMode.setTitle("Selecione os anúncios.");
            actionMode.setSubtitle(null);
            List<Ad> selectedAds = getSelectedAnuncios();
            if (selectedAds.size() == 0) {
                actionMode.finish();
            } else if (selectedAds.size() == 1) {
                actionMode.setSubtitle("1 anuncio selecionado");
            } else if (selectedAds.size() > 1) {
                actionMode.setSubtitle(selectedAds.size() + " anúncios selecionados");
            }
            updateShareIntent(selectedAds);
        }
    }

    // Atualiza a share intent com os ads selecionados
    private void updateShareIntent(List<Ad> selectedAds) {
        if (shareIntent != null) {
            // Texto com os anúncios
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Anúncios: " + selectedAds);
        }
    }

    // Retorna a lista de ads selecionados
    private List<Ad> getSelectedAnuncios() {
        List<Ad> list = new ArrayList<Ad>();
        for (Ad c : ads) {
            if (c.selected) {
                list.add(c);
            }
        }
        return list;
    }

    private ActionMode.Callback getActionModeCallback() {
        return new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                // Infla o menu específico da action bar de contexto (CAB)
                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.menu_selecao, menu);
                telaAtualIconesVisivelOuNaoMenuSelecao(menu);
                MenuItem shareItem = menu.findItem(R.id.action_share);
               /* ShareActionProvider share = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
                shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                shareIntent.setType("text/plain");
                share.setShareIntent(shareIntent);*/

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                final List<Ad> selectedAds = getSelectedAnuncios();
                if (item.getItemId() == R.id.action_add_lista_desejo_finalm) {
                    SimOuNaoDialog.show(getFragmentManager(), "Deseja adicionar esses anúncios que foram selecionados a sua lista de desejos?", new SimOuNaoDialog.Callback() {
                        @Override
                        public void metodoSimAoDialog() {
                            if (SessaoApplication.getInstance().getTipoDeUserLogado().equals("customer")) {
                                startTask("ads", new PostOuDeleteTask(selectedAds,"post"));
                            }else{
                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else if (item.getItemId() == R.id.action_delete_item_lista_finalm) {
                    SimOuNaoDialog.show(getFragmentManager(), "Deseja mesmo excluir esses anúncios da sua lista?", new SimOuNaoDialog.Callback() {
                        @Override
                        public void metodoSimAoDialog() {
                            Log.i("opaaa a lista aq",selectedAds.toString());
                            startTask("ads", new PostOuDeleteTask(selectedAds,"delete"));
                        }
                    });
                } else if ((item.getItemId() == R.id.action_share)) {
                    // Dispara a tarefa para fazer download das fotos
                    startTask("compartilhar", new CompartilharTask(selectedAds));

                } // Encerra o action mode
                mode.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // Limpa o estado
                actionMode = null;
                // Configura todos os anuncios para não selecionados
                for (Ad c : ads) {
                    c.selected = false;
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        };
    }

    private void telaAtualIconesVisivelOuNaoMenuSelecao(Menu menuSelecao) {
        if (SessaoApplication.getInstance().getTelaAtual().equals(TelaInicialClienteActivity.class)) {
            menuSelecao.findItem(R.id.action_delete_item_lista_finalm).setVisible(false);
            menuSelecao.findItem(R.id.action_add_lista_desejo_finalm).setVisible(true);
        } else {
            menuSelecao.findItem(R.id.action_delete_item_lista_finalm).setVisible(true);
            menuSelecao.findItem(R.id.action_add_lista_desejo_finalm).setVisible(false);
        }
    }

    // Task para fazer o download
    // Faça import da classe android.net.Uri;
    private class CompartilharTask implements TaskListener {
        private final List<Ad> selectedAds;
        // Lista de arquivos para compartilhar
        ArrayList<Uri> imageUris = new ArrayList<Uri>();

        public CompartilharTask(List<Ad> selectedAds) {
            this.selectedAds = selectedAds;
        }

        @Override
        public Object execute() throws Exception {
            if (selectedAds != null) {
                for (Ad c : selectedAds) {
                    // Faz o download da foto do anuncio para arquivo
                   // String url = c.urlFoto;
                    String url="http://i.imgur.com/DvpvklR.png";
                    String fileName = url.substring(url.lastIndexOf("/"));
                    // Cria o arquivo no SD card
                    File file = SDCardUtils.getPrivateFile(getContext(), "anuncios", fileName);
                    IOUtils.downloadToFile(url,file);
                    // Salva a Uri para compartilhar a foto
                    imageUris.add(Uri.fromFile(file));
                }
            }
            return null;
        }

        @Override
        public void updateView(Object o) {
            // Cria a intent com a foto dos anuncios
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            shareIntent.setType("image/*");
            // Cria o Intent Chooser com as opções
            startActivity(Intent.createChooser(shareIntent, "Enviar anúncios"));
        }

        @Override
        public void onError(Exception e) {
            alert("Ocorreu algum erro ao compartilhar.");
        }

        @Override
        public void onCancelled(String s) {
        }
    }

    private class PostOuDeleteTask implements TaskListener<List> {
        private final List<Ad> selectedAds;
        private String tipoReq="";

        public PostOuDeleteTask(List<Ad> selectedAds, String tipoReq) {
            this.selectedAds = selectedAds;
            this.tipoReq=tipoReq;
        }

        @Override
        public List execute() throws Exception {
            if (selectedAds != null) {
                Log.d("INTERESSANTE", tipoReq);
                if (tipoReq.equals("post")){
                    AnuncioEmComumService.addItensLista(selectedAds);
                    snack(recyclerView, "Anúncios adicionados na lista de desejo com sucesso.");
                }else if(tipoReq.equals("delete")){
                    AnuncioEmComumService.deleteItensLista(selectedAds);
                    snack(recyclerView, "Anúncios excluídos da lista com sucesso.");
                }
            }
            //Log.d("tiporetornado",tipo);
            return AnuncioEmComumService.getAnunciosByTipo(tipo);
            // Busca os anuncios em background (Thread)
        }
        @Override
        public void updateView(List ads) {
            if (ads != null) {
                // Salva a lista de anuncios no atributo da classe
                AnunciosOutroFragment.this.ads = ads;
                // Atualiza a view na UI Thread
                recyclerView.setAdapter(new AnuncioAdapter(getContext(), ads, onClickAnuncio()));
            }
        }

        @Override
        public void onError(Exception e) {
            // Qualquer exceção lançada no método execute vai cair aqui.
            if (e instanceof SocketTimeoutException) {
                alert(getString(R.string.msg_erro_io_timeout));
            } else {
               // alert(getString(R.string.msg_error_io));
            }
        }

        @Override
        public void onCancelled(String s) {
        }
    }

    public void search(String texto){
        tempAds = new ArrayList<>();
        for (Ad ad: ads){
            if (ad.getTitle().toLowerCase().contains(texto)){
                tempAds.add(ad);
            }
        }
        if (tempAds.size() == 0){
            Toast.makeText(getActivity(), "Nenhum anúncio encontrado", Toast.LENGTH_SHORT).show();
        }
        recyclerView.setAdapter(new AnuncioAdapter(getContext(), tempAds, onClickAnuncio()));
    }

    public void closeSearch(){
        tempAds = null;
        recyclerView.setAdapter(new AnuncioAdapter(getContext(), ads, onClickAnuncio()));
    }
}

