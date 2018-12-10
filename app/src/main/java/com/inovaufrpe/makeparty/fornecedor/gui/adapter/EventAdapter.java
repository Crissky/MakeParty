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

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventsViewHolder> {
    private final List<Event> events;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    private final Context context;
    private final EventAdapter.EventOnClickListener onClickListener;

    //abaixo metodos que devem ser implementados para ter diferentes respostas dependendo do clique
    public interface EventOnClickListener {
        void onClickEvent(EventAdapter.EventsViewHolder holder, int indexEvent);
    }
    //Aqui esta informando que esse adapter , essa classe EventAdapter esta personalizando cada item de uma lista
    // uma lista de avaliacoes que no caso ficará "guardada" em Events
    public EventAdapter(Context context, List<Event> events, EventAdapter.EventOnClickListener onClickListener) {
        this.context = context;
        this.events = events;
        this.onClickListener = onClickListener;
    }
    //aqui embaixo esta informando qual xml ta desenhando o item de cada lista, esta personalizando o item
    @Override
    public EventAdapter.EventsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        //ESSE VIEWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW AQ DEBAXO TA ERRADO NEHH
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_comentario_anuncio, viewGroup, false);
        // Cria a classe do ViewHolder
        EventAdapter.EventsViewHolder holder = new EventAdapter.EventsViewHolder(view);
        return holder;
    }
    //aqui embaixo "implementa" parte 1 doq acontece ao selecionar um item clique normal
    //seta os itens view do item, trocando pelas informações corretas em cada campo
    @Override
    public void onBindViewHolder(final EventAdapter.EventsViewHolder holder, final int position) {
        // Rating da linha
        Event eventw = events.get(position);

        // Atualizada os valores nas views
        //holder.nomeCliente.setText(ava.get);
        // holder.tDescComent.setText(("Descrição:"+ava.getDescriptionComment()));

        /*SimpleDateFormat out = new SimpleDateFormat("dd/MM/yyyy",new Locale("pt","BR"));
        String result = out.format(new Date(String.valueOf(ava.getDateComment())));
        holder.dataComentCliente.setText(("Data: "+ result));
        */
        //holder.estrelasNotaAvaliacaoCliente.setRating(ava.getRatingUser().intValue());
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView); HMMMMMMM

        // Foto do perfil da pessoa?
        //ImageUtils.setImage(context,"http://i.imgur.com/DvpvklR.png", holder.img);
        //holder.menuMiniOpcoesComentEspecif.                      -- nn, n sei como fazer p mostrar aq nesse menuMini

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickEvent(holder, position);
                }
            });
        }
        //ads.get(position).getSelecionado()
        //int corFundo = context.getResources().getColor(ava.selected ? R.color.colorRosaClaro : R.color.colorWhite);

        //holder.cardView.setCardBackgroundColor(corFundo);
    }
    //metodo abaixo retorna quantos itens anuncio tem
    @Override
    public int getItemCount() {
        return this.events != null ? this.events.size() : 0;
    }

    //metodo abaixo diz q é classe view e que herda de RecyclerView.ViewHolder
    //declara os itens view do adapter de anuncio (o que tem de view em cada item da lista )
    public static class EventsViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public ImageView img;
        public TextView nomeCliente;
        public TextView tDescComent;
        public RatingBar estrelasNotaAvaliacaoCliente;
        public TextView dataComentCliente;
        ImageButton menuMiniOpcoesComentEspecif;


        private ProgressBar progress;
        public View view;

        public EventsViewHolder(View view) {
            super(view);
            this.view = view;

            //// Cria as views para salvar no ViewHolder
            cardView = view.findViewById(R.id.card_view);
            img = (ImageView) view.findViewById(R.id.img);
            nomeCliente = (TextView) view.findViewById(R.id.textView);
            tDescComent = (TextView) view.findViewById(R.id.text);
            estrelasNotaAvaliacaoCliente = (RatingBar) view.findViewById(R.id.ratingBarJaAvaliadoComent);
            dataComentCliente = (TextView) view.findViewById(R.id.textViewDataAvaliacaoAnuncioCliente);
            progress = (ProgressBar) view.findViewById(R.id.progress);
            cardView = (CardView) view.findViewById(R.id.card_view);
            menuMiniOpcoesComentEspecif =(ImageButton) view.findViewById(R.id.menuMiniOpcoesComentEspecif);

        }

    }
}




