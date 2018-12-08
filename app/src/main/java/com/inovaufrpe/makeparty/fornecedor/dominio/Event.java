package com.inovaufrpe.makeparty.fornecedor.dominio;

import com.inovaufrpe.makeparty.usuario.dominio.Address;

import java.util.Date;

public class Event {

    private Owner owner;
    private String advertiser;
    private String client;
    private String startDate;
    private String endDate;
    private String description;
    private String type;
    private Address address;

    public Event(Owner owner, String advertiser, String client, String startDate, String endDate, String description, String type, Address address) {
        this.owner = owner;
        this.advertiser = advertiser;
        this.client = client;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.type = type;
        this.address = address;
    }

    public Event(){

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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





}
