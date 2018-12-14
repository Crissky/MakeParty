package com.inovaufrpe.makeparty.fornecedor.gui.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Event;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EventFornAdapter extends RecyclerView.Adapter<EventFornAdapter.EventsFornViewHolder> {
    private final List<Event> events;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private final Context context;
    private final EventFornAdapter.EventFornOnClickListener onClickListener;

    //abaixo metodos que devem ser implementados para ter diferentes respostas dependendo do clique
    public interface EventFornOnClickListener {
        void onClickEventForn(EventFornAdapter.EventsFornViewHolder holder, int indexEvent);

        void onLongClickEventForn(EventsFornViewHolder holder, int idx);
    }
    //Aqui esta informando que esse adapter , essa classe EventFornAdapter esta personalizando cada item de uma lista
    // uma lista de avaliacoes que no caso ficará "guardada" em Events
    public EventFornAdapter(Context context, List<Event> events, EventFornAdapter.EventFornOnClickListener onClickListener) {
        this.context = context;
        this.events = events;
        this.onClickListener = onClickListener;
    }
    //aqui embaixo esta informando qual xml ta desenhando o item de cada lista, esta personalizando o item
    @Override
    public EventFornAdapter.EventsFornViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_evento_forn, viewGroup, false);
        // Cria a classe do ViewHolder
        EventFornAdapter.EventsFornViewHolder holder = new EventFornAdapter.EventsFornViewHolder(view);
        return holder;
    }
    //aqui embaixo "implementa" parte 1 doq acontece ao selecionar um item clique normal
    //seta os itens view do item, trocando pelas informações corretas em cada campo
    @Override
    public void onBindViewHolder(final EventFornAdapter.EventsFornViewHolder holder, final int position) {
        // evento da linha
        Event eventoGet = events.get(position);

        // Atualizada os valores nas views
        holder.tipoEvento.setText(("Tipo do evento :"+eventoGet.getType()));
        holder.nomeCliente.setText(("Nome do cliente :"+eventoGet.getClient()));
        SimpleDateFormat sdfDiaMesAno = new SimpleDateFormat("dd/MM/yyyy",new Locale("pt","BR"));
        SimpleDateFormat sdfHoraMin = new SimpleDateFormat("HH:mm",new Locale("pt","BR"));
        String diaMesAnoEventoInicio = sdfDiaMesAno.format(eventoGet.getStartDate());
        String horaMinEventoInicio = sdfHoraMin.format(eventoGet.getStartDate());
        holder.dataInicioEvento.setText(("Data de ínicio :"+ diaMesAnoEventoInicio+" às "+horaMinEventoInicio));
        String diaMesAnoEventoFim = sdfDiaMesAno.format(eventoGet.getStartDate());
        String horaMinEventoFim = sdfHoraMin.format(eventoGet.getStartDate());
        holder.dataFimEvento.setText(("Data fim do evento :"+ diaMesAnoEventoFim+" às "+horaMinEventoFim));
        holder.situacaoEvento.setText(("Situação: "));


        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickEventForn(holder, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickListener.onLongClickEventForn(holder, position);
                    return true;
                }
            });
        }
        int corFundo = context.getResources().getColor(eventoGet.selected ? R.color.colorRosaClaro : R.color.colorWhite);

        holder.cardView.setCardBackgroundColor(corFundo);
    }
    //metodo abaixo retorna quantos itens anuncio tem
    @Override
    public int getItemCount() {
        return this.events != null ? this.events.size() : 0;
    }

    //metodo abaixo diz q é classe view e que herda de RecyclerView.ViewHolder
    //declara os itens view do adapter de anuncio (o que tem de view em cada item da lista )
    public static class EventsFornViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public TextView tipoEvento;
        public TextView nomeCliente;
        public TextView dataInicioEvento;
        public TextView dataFimEvento;
        public TextView situacaoEvento;

        private ProgressBar progress;
        public View view;

        public EventsFornViewHolder(View view) {
            super(view);
            this.view = view;

            //// Cria as views para salvar no ViewHolder
            cardView = view.findViewById(R.id.card_view);
            tipoEvento = (TextView) view.findViewById(R.id.textViewTipoEventoForn);
            nomeCliente = (TextView) view.findViewById(R.id.textViewNomeClienteEvento);
            dataInicioEvento =(TextView) view.findViewById(R.id.textViewDataInicioEventoListaForn);
            dataFimEvento = (TextView) view.findViewById(R.id.textViewDataFimEventoListaForn);
            situacaoEvento = (TextView) view.findViewById(R.id.textViewSituacaoEventoListaForn);
            progress = (ProgressBar) view.findViewById(R.id.progress);
            cardView = (CardView) view.findViewById(R.id.card_view);

        }

    }
}




