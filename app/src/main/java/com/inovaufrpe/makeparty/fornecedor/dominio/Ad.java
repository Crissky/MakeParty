package com.inovaufrpe.makeparty.fornecedor.dominio;

import android.os.Parcel;
import android.os.Parcelable;

import com.inovaufrpe.makeparty.user.dominio.Address;

import java.util.ArrayList;
import java.util.Date;


//TEM QUE MUDAR AQUI DEPOIS, PARA "AD" MAS TEM QUE ADAPTAR
public class Ad implements Parcelable {

    public String _id;
    private String title;
    private Date createdAt;
    private Date updatedAt;
    private String description;
    private double price;
    private Address address;
    private String phone;
    private String type;
    private ArrayList tags;
    private Owner owner;
    private ArrayList photos;

    // Flag para a action bar de contexto
    public boolean selected;
    // Está favoritado se vem do banco de dadosine
    public boolean favorited;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date dataAnuncio) {
        this.createdAt = dataAnuncio;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public ArrayList getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList photos) {
        this.photos = photos;
    }


    /*@Override
    public String toString() {
        try {
            JSONObject o = new JSONObject().put("id", _id).put("title", title).put("price", price).put("description", description)
                    .put("type", type).put("photos", photos);
            return o.toString();
        } catch (JSONException e) {
            System.out.println("Erro no toString do Filme JSON: "+ e.getMessage());
        }
        return null;
    }*/

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Escreve os dados para serem serializados
        dest.writeString(_id);
        dest.writeString(this.title);
        dest.writeString(this.description);
        //dest.writeString(this.urlFoto);
        dest.writeDouble(this.price);
        //dest.writeString(this.urlVideo);
        dest.writeString(this.phone);
        //dest.writeString(this.address);
        dest.writeString(String.valueOf(this.address));
    }

    public void readFromParcel(Parcel parcel) {
        // Lê os dados na mesma ordem em que foram escritos
        this._id = parcel.readString();
        this.title = parcel.readString();
        this.description = parcel.readString();
        this.price = parcel.readDouble();
        //this.urlFoto = parcel.readString();
        this.phone = parcel.readString();
        //this.photos = parcel.readArrayList();
        //this.owner = parcel.readP

    }

    public static final Parcelable.Creator<Ad> CREATOR = new Parcelable.Creator<Ad>() {
        @Override
        public Ad createFromParcel(Parcel p) {
            Ad c = new Ad();
            c.readFromParcel(p);
            return c;
        }
        @Override
        public Ad[] newArray(int size) {
            return new Ad[size];
        }
    };


    @Override
    public String toString() {
        return "Ad[" +
                "_id='" + _id + '\'' +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", address=" + address +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                ", tags=" + tags +
                ", owner=" + owner +
                ", photos=" + photos +
                ", selected=" + selected +
                ", favorited=" + favorited +
                ']';
    }
}
