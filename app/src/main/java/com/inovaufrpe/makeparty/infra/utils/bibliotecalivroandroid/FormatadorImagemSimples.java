package com.inovaufrpe.makeparty.infra.utils.bibliotecalivroandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.inovaufrpe.makeparty.infra.SessaoApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

//Vindo do Hamba

public class FormatadorImagemSimples {

    public byte[] getFotoByte(int idResource){
        byte[] fotoByte = this.bitMapParaByte(gerarBitMap(idResource));
        return fotoByte;
    }

    public Bitmap byteArrayToBitmap(byte[] byteArray) {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(byteArray);
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        return bitmap;
    }

    private Bitmap gerarBitMap(int idResource){
        Bitmap bitmap = BitmapFactory.decodeResource(SessaoApplication.getContext().getResources(), idResource, new BitmapFactory.Options());
        return bitmap;
    }

    private byte[] bitMapParaByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte imagemBytes[] = stream.toByteArray();
        return imagemBytes;
    }

    public ArrayList<Bitmap> listByteToListBitmap (ArrayList<byte[]> imagens){
        ArrayList<Bitmap> bitMapList = new ArrayList<>();
        for (byte[] imagem: imagens) {
            bitMapList.add(byteArrayToBitmap(imagem));
        }
        return bitMapList;
    }
}