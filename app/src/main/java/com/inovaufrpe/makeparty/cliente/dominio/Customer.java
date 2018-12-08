package com.inovaufrpe.makeparty.cliente.dominio;

import com.inovaufrpe.makeparty.user.dominio.User;

public class Customer {

    private String name;
    private String cpf;
    private String birthdate;
    private String phone;
    private User user;
    //private Byte photo;
    //private String photo;

    public Customer(User user, String name, String cpf, String birthdate, String phone) {
        this.name = name;
        this.cpf = cpf;
        this.birthdate = birthdate;
        this.phone = phone;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //public String getPhotos() {
      //  return photo;
    //}

    //public void setPhotos(String photo) {
      //  this.photo = photo;
    //}



}
