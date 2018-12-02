package com.inovaufrpe.makeparty.usuario.gui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;

import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.AnunciosViewHolder> {
    private final List<Ads> ads;
    private final Context context;
    private final AnuncioOnClickListener onClickListener;

    //abaixo metodos que devem ser implementados para ter diferentes respostas dependendo do clique
    public interface AnuncioOnClickListener {
        void onClickAnuncio(AnunciosViewHolder holder, int indexAnuncio);
        void onLongClickAnuncio(AnunciosViewHolder holder, int indexAnuncio);
    }

    //Aqui esta informando que esse adapter , essa classe AnuncioAdapter esta personalizando cada item de uma lista
    // uma lista de ads que no caso ficará "guardada" em AnuncioView
    public AnuncioAdapter(Context context, List<Ads> ads, AnuncioOnClickListener onClickListener) {
        this.context = context;
        this.ads = ads;
        this.onClickListener = onClickListener;
    }
    //aqui embaixo esta informando qual xml ta desenhando o item de cada lista, esta personalizando o item
    @Override
    public AnunciosViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Este método cria uma subclasse de RecyclerView.ViewHolder
        // Infla a view do layout
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_anuncio, viewGroup, false);
        // Cria a classe do ViewHolder
        AnunciosViewHolder holder = new AnunciosViewHolder(view);
        return holder;
    }
    //aqui embaixo "implementa" parte 1 doq acontece ao selecionar um item, clique longo e clique normal
    //seta os itens view do item, trocando pelas informações corretas em cada campo
    @Override
    public void onBindViewHolder(final AnunciosViewHolder holder, final int position) {
        //Ads anuncio = ads.get(position).getAnuncio(); --hamba
        //Bitmap imagemAnuncio = anuncio.getImagemBitmap(); --hamba
        //holder.imageView.setImageBitmap(imagemAnuncio); --hamba
        // Ads da linha
        Ads c = ads.get(position);

        // Atualizada os valores nas views
        holder.title.setText(c.getTitle());
        holder.tDesc.setText(c.getDescription());

        // Foto do Ads
        //ImageUtils.setImage(context, c.urlFoto, holder.img);

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickAnuncio(holder, position);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickListener.onLongClickAnuncio(holder, position);
                    return true;
                }
            });
        }
        //ads.get(position).getSelecionado()
        int corFundo = context.getResources().getColor(c.selected ? R.color.colorRosaClaro : R.color.colorWhite);

        holder.cardView.setCardBackgroundColor(corFundo);
    }
    //metodo abaixo retorna quantos itens anuncio tem
    @Override
    public int getItemCount() {
        return this.ads != null ? this.ads.size() : 0;
    }

    //metodo abaixo diz q é classe view e que herda de RecyclerView.ViewHolder
    //declara os itens view do adapter de anuncio (o que tem de view em cada item da lista )
    public static class AnunciosViewHolder extends RecyclerView.ViewHolder {
         CardView cardView;
        public ImageView img;
        public TextView title;
        public TextView tDesc;
        private ProgressBar progress;
        public View view;

        public AnunciosViewHolder(View view) {
            super(view);
            this.view = view;

            //// Cria as views para salvar no ViewHolder
            cardView = view.findViewById(R.id.card_view);
            img = (ImageView) view.findViewById(R.id.img);
            title = (TextView) view.findViewById(R.id.textViewTitleAnuncio);
            tDesc = (TextView) view.findViewById(R.id.textViewRazaoSocialFornecedor);
            progress = (ProgressBar) view.findViewById(R.id.progress);
            cardView = (CardView) view.findViewById(R.id.card_view);

        }
    }
}