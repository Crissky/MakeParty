package com.inovaufrpe.makeparty.cliente.gui.adapter;

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
import com.inovaufrpe.makeparty.cliente.dominio.Rating;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class ComentarioDoAnuncioAdapter extends RecyclerView.Adapter<ComentarioDoAnuncioAdapter.ComentariosDoAnuncioViewHolder> {
    private final List<Rating> ratings;
    private final Context context;
    private final ComentarioDoAnuncioAdapter.ComentarioDoAnuncioOnClickListener onClickListener;

    //abaixo metodos que devem ser implementados para ter diferentes respostas dependendo do clique
    public interface ComentarioDoAnuncioOnClickListener {
        void onClickComentario(ComentarioDoAnuncioAdapter.ComentariosDoAnuncioViewHolder holder, int indexComentario);
    }
    //Aqui esta informando que esse adapter , essa classe ComentarioDoAnuncioAdapter esta personalizando cada item de uma lista
    // uma lista de avaliacoes que no caso ficará "guardada" em Avaliacoes
    public ComentarioDoAnuncioAdapter(Context context, List<Rating> ratings, ComentarioDoAnuncioAdapter.ComentarioDoAnuncioOnClickListener onClickListener) {
        this.context = context;
        this.ratings = ratings;
        this.onClickListener = onClickListener;
    }
    //aqui embaixo esta informando qual xml ta desenhando o item de cada lista, esta personalizando o item
    @Override
    public ComentarioDoAnuncioAdapter.ComentariosDoAnuncioViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_comentario_anuncio, viewGroup, false);
        // Cria a classe do ViewHolder
        ComentarioDoAnuncioAdapter.ComentariosDoAnuncioViewHolder holder = new ComentarioDoAnuncioAdapter.ComentariosDoAnuncioViewHolder(view);
        return holder;
    }
    //aqui embaixo "implementa" parte 1 doq acontece ao selecionar um item clique normal
    //seta os itens view do item, trocando pelas informações corretas em cada campo
    @Override
    public void onBindViewHolder(final ComentarioDoAnuncioAdapter.ComentariosDoAnuncioViewHolder holder, final int position) {
        // Rating da linha
        Rating ava = ratings.get(position);

        // Atualizada os valores nas views
        holder.nomeCliente.setText(ava.getNameClient().toString());
        holder.tDescComent.setText(("Descrição:"+ava.getDescriptionComment()));
        SimpleDateFormat sdfDiaMesAno = new SimpleDateFormat("dd/MM/yyyy",new Locale("pt","BR"));
        SimpleDateFormat sdfHoraMin = new SimpleDateFormat("HH:mm",new Locale("pt","BR"));
        String diaMesAnoCriadoComent = sdfDiaMesAno.format(ava.getDateComment());
        String horaMinCriadoComent = sdfHoraMin.format(ava.getDateComment());
        holder.dataComentCliente.setText(("Data de inicio: "+ diaMesAnoCriadoComent+" às "+horaMinCriadoComent));
        holder.estrelasNotaAvaliacaoCliente.setRating(ava.getRating());
        //Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView); HMMMMMMM

        // Foto do perfil da pessoa?
        //ImageUtils.setImage(context,"http://i.imgur.com/DvpvklR.png", holder.img);
        //holder.menuMiniOpcoesComentEspecif.                      -- nn, n sei como fazer p mostrar aq nesse menuMini

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickComentario(holder, position);
                }
            });
        }
        //ads.get(position).getSelecionado()
        int corFundo = context.getResources().getColor(ava.selected ? R.color.colorRosaClaro : R.color.colorWhite);

        holder.cardView.setCardBackgroundColor(corFundo);
    }
    //metodo abaixo retorna quantos itens anuncio tem
    @Override
    public int getItemCount() {
        return this.ratings != null ? this.ratings.size() : 0;
    }

    //metodo abaixo diz q é classe view e que herda de RecyclerView.ViewHolder
    //declara os itens view do adapter de anuncio (o que tem de view em cada item da lista )
    public static class ComentariosDoAnuncioViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public ImageView img;
        public TextView nomeCliente;
        public TextView tDescComent;
        public RatingBar estrelasNotaAvaliacaoCliente;
        public TextView dataComentCliente;
        ImageButton menuMiniOpcoesComentEspecif;


        private ProgressBar progress;
        public View view;

        public ComentariosDoAnuncioViewHolder(View view) {
            super(view);
            this.view = view;

            //// Cria as views para salvar no ViewHolder
            cardView = view.findViewById(R.id.card_view);
            img = (ImageView) view.findViewById(R.id.img);
            nomeCliente = (TextView) view.findViewById(R.id.textViewNomeCliente);
            tDescComent = (TextView) view.findViewById(R.id.text);
            estrelasNotaAvaliacaoCliente = (RatingBar) view.findViewById(R.id.ratingBarJaAvaliadoComent);
            dataComentCliente = (TextView) view.findViewById(R.id.textViewDataAvaliacaoAnuncioCliente);
            progress = (ProgressBar) view.findViewById(R.id.progress);
            cardView = (CardView) view.findViewById(R.id.card_view);
            menuMiniOpcoesComentEspecif =(ImageButton) view.findViewById(R.id.menuMiniOpcoesComentEspecif);

        }

        public void estrelasNotaAvaliacaoCliente() {
        }
        public void menuOptComent(){

        }
    }
}




