package com.inovaufrpe.makeparty.fornecedor.gui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.inovaufrpe.makeparty.R;

import java.util.ArrayList;

public class TesteCapturaGaleriaActivity extends AppCompatActivity {

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

        imagem1= (ImageView) findViewById(R.id.imageView1);
        imagem2= (ImageView) findViewById(R.id.imageView2);
        imagem3= (ImageView) findViewById(R.id.imageView3);
        imagem4= (ImageView) findViewById(R.id.imageView4);
        botao = (Button) findViewById(R.id.botao);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new   Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALERIA_IMAGENS);
            }
        });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (resultCode== RESULT_OK && requestCode== GALERIA_IMAGENS) {
        Uri selectedImage= data.getData();
        String[] filePath= { MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
        c.moveToFirst();
        int columnIndex= c.getColumnIndex(filePath[0]);
        String picturePath= c.getString(columnIndex);c.close();
        Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
        imagem1.setImageBitmap(thumbnail);      /// imagem sendo setado na tela
        imagem2.setImageBitmap(thumbnail);      /// imagem setado na tela
        imagem3.setImageBitmap(thumbnail);      /// imagem setado na tela
        imagem4.setImageBitmap(thumbnail);      /// imagem setado na tela


    }




    }

    private void mudarTela(Class proximaTela){
        Intent intent = new Intent( TesteCapturaGaleriaActivity.this, proximaTela);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        this.mudarTela(CriarAnuncioActivity.class);
    }

}
