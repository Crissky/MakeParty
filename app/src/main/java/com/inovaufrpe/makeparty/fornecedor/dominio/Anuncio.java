package com.inovaufrpe.makeparty.fornecedor.dominio;

import android.os.Parcel;
import android.os.Parcelable;

import com.inovaufrpe.makeparty.fornecedor.dominio.PessoaJuridica;
import com.inovaufrpe.makeparty.usuario.dominio.Endereco;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Anuncio implements Parcelable {

    public Long id;
    private String title;
    private Date dataAd;
    private String description;
    private double price;
    private Endereco address;
    private String phone;
    private String type;
    private ArrayList tags;
    private PessoaJuridica socialname;
    private PessoaJuridica owner;
    private ArrayList photo;
    //private ArrayList<> photos;                  ARRAYLIST<OBJETO???>

    // Flag para a action bar de contexto
    public boolean selected;
    // Está favoritado se vem do banco de dadosine
    public boolean favorited;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDataAd() {
        return dataAd;
    }

    public void setDataAd(Date dataAnuncio) {
        this.dataAd = dataAnuncio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Endereco getAddress() {
        return address;
    }

    public void setAddress(Endereco endereco) {
        this.address = endereco;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList getTags() {
        return tags;
    }

    public void setTags(ArrayList tags) {
        this.tags = tags;
    }
    public PessoaJuridica getSocialname() {
        return socialname;
    }

    public void setSocialname(PessoaJuridica socialname) {
        this.socialname = socialname;
    }

    public PessoaJuridica getOwner() {
        return owner;
    }

    public void setOwner(PessoaJuridica owner) {
        this.owner = owner;
    }

    public ArrayList getPhoto() {
        return photo;
    }

    public void setPhoto(ArrayList photo) {
        this.photo = photo;
    }
   /* @Override
   public String toString() {
        return "Anuncio{" + "nome='" + nome + '\'' + ", desc='" + desc + '\'' + '}';
    }*/
    @Override
    public String toString() {
        try {
            JSONObject o = new JSONObject().put("id", id).put("title", title).put("price", price).put("description", description)
                    .put("type", type).put("photo", photo);
            return o.toString();
        } catch (JSONException e) {
            System.out.println("Erro no toString do Filme JSON: "+ e.getMessage());
        }
        return null;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Escreve os dados para serem serializados
        dest.writeLong(id);
        dest.writeString(this.title);
        //dest.writeString(this.owner.getSocialname());
        dest.writeString(this.description);
        //dest.writeString(this.urlFoto);
        dest.writeDouble(this.price);
        //dest.writeString(this.urlVideo);
        dest.writeString(this.phone);
        //dest.writeString(this.address);
    }

    public void readFromParcel(Parcel parcel) {
        // Lê os dados na mesma ordem em que foram escritos
        this.id = parcel.readLong();
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.price = parcel.readDouble();
        //this.urlFoto = parcel.readString();
        this.phone = parcel.readString();
    }

    public static final Parcelable.Creator<Anuncio> CREATOR = new Parcelable.Creator<Anuncio>() {
        @Override
        public Anuncio createFromParcel(Parcel p) {
            Anuncio c = new Anuncio();
            c.readFromParcel(p);
            return c;
        }
        @Override
        public Anuncio[] newArray(int size) {
            return new Anuncio[size];
        }
    };


}
