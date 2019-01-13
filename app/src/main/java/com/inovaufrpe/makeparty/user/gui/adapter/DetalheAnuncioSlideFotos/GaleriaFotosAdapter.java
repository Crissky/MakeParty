package com.inovaufrpe.makeparty.user.gui.adapter.DetalheAnuncioSlideFotos;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.inovaufrpe.makeparty.R;

import java.util.ArrayList;
import java.util.List;

public class GaleriaFotosAdapter extends PagerAdapter {
    //Quando recebe a lista de imagens em bitmap eu vejo a qtd de itens, se for 0 , coloco um icone sem foto, se n, a qtdFotos = qtdRecebida
    //Aqui se recebe uma lista com array de bitmaps e faz a troca das fotos
    //private Integer[] images = {R.drawable.placeholder, R.drawable.placeholder};
    private List<Bitmap> imagens;
    private Context context;
    private LayoutInflater layoutInflater;
    private int qtdFotos=1;

    public GaleriaFotosAdapter(Context context, ArrayList<Bitmap> imagens) {
        this.context = context;
        this.imagens = imagens;
        if (this.imagens.isEmpty()) {
            Bitmap iconeSemFoto = BitmapFactory.decodeResource(context.getResources(), R.drawable.placeholder);
            imagens.add(iconeSemFoto);
        }
        this.qtdFotos = imagens.size();
    }
    @Override
    public int getCount() {
        System.out.println(Log.d("QTDFOTOS",String.valueOf(qtdFotos)));
        return qtdFotos;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.viewpager_item, null);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.imageDetalhe);
        Bitmap imagemBitMap = imagens.get(position);
        imageView.setImageBitmap(imagemBitMap);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(item_view, 0);
        return item_view;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }


}