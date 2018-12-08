package com.inovaufrpe.makeparty.fornecedor.dominio;


import com.inovaufrpe.makeparty.usuario.dominio.Usuario;

public class Owner {

    private String socialname;
    private String cnpj;
    private String authorization;
    private String photo;
    //private Byte photo;
    private Usuario usuario;
    private String phone;
    private Plano plan;
    private Schedule schedule;


    public Owner() {
    }
    public Owner(Usuario usuario, String socialname, String cnpj, String phone) {
        this.socialname = socialname;
        this.cnpj = cnpj;
        this.usuario = usuario;
        this.phone = phone;
    }

    public String getSocialname() {
        return socialname;
    }

    public void setSocialname(String socialname) {
        this.socialname = socialname;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Plano getPlan() {
        return plan;
    }

    public void setPlan(Plano plan) {
        this.plan = plan;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }


    @Override
    public String toString() {
        return "Owner[" +
                "socialname='" + socialname + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", authorization='" + authorization + '\'' +
                ", photo='" + photo + '\'' +
                ", usuario=" + usuario +
                ", phone='" + phone + '\'' +
                ", plan=" + plan +
                ", schedule=" + schedule +
                ']';
    }
}

