package com.inovaufrpe.makeparty.cliente.gui.adapter.DetalheAnuncioSlideFotos;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.inovaufrpe.makeparty.R;

import java.util.ArrayList;
import java.util.List;

public class GaleriaFotosAdapter extends PagerAdapter {
    //POR ENQUANTO SÓ TA COM 2 FTS E TBM TA COM UMA LISTA BASE - TEM Q RETORNAR A LISTA DAS FOTOS DE VDD AINDA
    private Integer[] images = {R.drawable.ic_camera_24dp, R.drawable.ic_camera_24dp};
    private List<Bitmap> imagens;
    private Context context;
    private LayoutInflater layoutInflater;
    public GaleriaFotosAdapter(Context context, ArrayList<Bitmap> imagens) {
        this.context = context;
        this.imagens = imagens;
    }
    @Override
    public int getCount() {
        return images.length;
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.viewpager_item, null);
        Bitmap imagemBitMap = imagens.get(position);
        ImageView imageView = (ImageView) item_view.findViewById(R.id.imageDetalhe);
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