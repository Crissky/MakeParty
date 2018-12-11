package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.fornecedor.gui.adapter.FotosAnuncioAdapter;
import com.inovaufrpe.makeparty.infra.SessaoApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class TesteCapturaGaleriaActivity extends AppCompatActivity {
    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST_CODE = 228;
    private Button botaoSelecionarFoto;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_captura_galeria);
        SessaoApplication.getInstance().setTelaAtual(TesteCapturaGaleriaActivity.class);
        setTela();
        botaoSelecionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGaleriaClicked(v);
            }
        });
        }
    public void setTela(){
        botaoSelecionarFoto = findViewById(R.id.botao);
        recyclerView = findViewById(R.id.view);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        //adapter = new FotosAnuncioAdapter(bitmaps);
        recyclerView.setAdapter(adapter);
    }
    public void onGaleriaClicked(View v) {
        Intent selecionarFoto = new Intent(Intent.ACTION_PICK);
        File diretorioImagem = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String diretorioImagemPath = diretorioImagem.getPath();
        Uri data = Uri.parse(diretorioImagemPath);
        selecionarFoto.setDataAndType(data, "image/*");
        startActivityForResult(selecionarFoto, IMAGE_GALLERY_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST_CODE) {
                Toast.makeText(this, "Imagem salva", Toast.LENGTH_LONG).show();
            }
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.add(bitmap);
                    adapter.notifyDataSetChanged();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Não foi possível abrir a imagem", Toast.LENGTH_LONG).show();
                }

            }
        }
    }
    private void mudarTela(Class proximaTela){
        Intent intent = new Intent( TesteCapturaGaleriaActivity.this, proximaTela);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed(){
        this.mudarTela(SessaoApplication.getInstance().getTelaAnterior());
    }

}

