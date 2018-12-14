package com.inovaufrpe.makeparty.fornecedor.dominio;

import android.os.Parcel;
import android.os.Parcelable;

import com.inovaufrpe.makeparty.user.dominio.Address;

public class Event implements Parcelable {

    private String _id;
    private Owner owner;
    private String advertiser;
    private String client;
    private String startdate;
    private String enddate;
    private String description;
    private String type;
    private Address address;
    //esse atributo de selecionado n é p ta ai, mas funciona p saber se foi selecionado ou n
    public boolean selected;

    public Event(Owner owner, String advertiser, String client, String startdate, String enddate, String description, String type, Address address) {
        this.owner = owner;
        this.advertiser = advertiser;
        this.client = client;
        this.startdate = startdate;
        this.enddate = enddate;
        this.description = description;
        this.type = type;
        this.address = address;
    }

    public Event(){

    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(String advertiser) {
        this.advertiser = advertiser;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // Escreve os dados para serem serializados
        dest.writeString(_id);
        dest.writeString(this.client);
        dest.writeString(this.description);
        dest.writeString(this.type);
        dest.writeString(this.startdate);
        dest.writeString(this.enddate);
        dest.writeString(String.valueOf(this.address));
    }

    public void readFromParcel(Parcel parcel) {
        // Lê os dados na mesma ordem em que foram escritos
        this._id = parcel.readString();
        this.client = parcel.readString();
        this.description = parcel.readString();
        this.type = parcel.readString();
        this.startdate = parcel.readString();
        this.enddate = parcel.readString();

    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel p) {
            Event c = new Event();
            c.readFromParcel(p);
            return c;
        }
        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
