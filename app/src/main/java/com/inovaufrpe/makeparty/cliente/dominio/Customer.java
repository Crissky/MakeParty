package com.inovaufrpe.makeparty.cliente.dominio;

import com.inovaufrpe.makeparty.usuario.dominio.Usuario;

public class Customer {

    private String name;
    private String cpf;
    private String birthdate;
    private String phone;
    private Usuario usuario;
    //private Byte photo;
    //private String photo;

    public Customer(Usuario usuario, String name, String cpf, String birthdate, String phone) {
        this.name = name;
        this.cpf = cpf;
        this.birthdate = birthdate;
        this.phone = phone;
        this.usuario = usuario;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    //public String getPhotos() {
      //  return photo;
    //}

    //public void setPhotos(String photo) {
      //  this.photo = photo;
    //}



}
