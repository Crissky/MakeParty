package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.inovaufrpe.makeparty.R;
import com.inovaufrpe.makeparty.infra.SessaoApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class TesteCapturaGaleriaActivity extends AppCompatActivity {
    public static final int IMAGE_GALLERY_REQUEST = 20;
    public static final int CAMERA_REQUEST_CODE = 228;
    private ImageView imagem1;
    private ImageView imagem2;
    private ImageView imagem3;
    private ImageView imagem4;
    private Button botao ;
    private final int GALERIA_IMAGENS = 1;
    private ArrayList<ImageView> listaImagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_captura_galeria);
        SessaoApplication.getInstance().setTelaAtual(TesteCapturaGaleriaActivity.class);
        setTela();
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onGaleriaClicked(v);
            }
        });
        }
    public void setTela(){
        imagem1= findViewById(R.id.imageView1);
        imagem2= findViewById(R.id.imageView2);
        imagem3= findViewById(R.id.imageView3);
        imagem4= findViewById(R.id.imageView4);
        botao = findViewById(R.id.botao);
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
                    Bitmap image = BitmapFactory.decodeStream(inputStream);
                    imagem1.setImageBitmap(image);
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

