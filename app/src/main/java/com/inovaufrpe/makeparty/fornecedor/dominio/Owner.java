package com.inovaufrpe.makeparty.fornecedor.dominio;


import com.inovaufrpe.makeparty.user.dominio.User;

public class Owner {

    private String _id;
    private String socialname;
    private String cnpj;
    private String authorization;
    private String photo;
    //private Byte photo;
    private User user;
    private String phone;
    private Plano plan;
    private Schedule schedule;


    public Owner() {
    }
    public Owner(User user, String socialname, String cnpj, String phone) {
        this.socialname = socialname;
        this.cnpj = cnpj;
        this.user = user;
        this.phone = phone;
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
                ", user=" + user +
                ", phone='" + phone + '\'' +
                ", plan=" + plan +
                ", schedule=" + schedule +
                ']';
    }
}

