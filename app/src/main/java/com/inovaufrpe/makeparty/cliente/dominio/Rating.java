package com.inovaufrpe.makeparty.cliente.dominio;

import java.util.Date;

public class Rating {


    //private Ad ad;
    private String ad;
    private String comment;
    private Integer rating;
    private Customer nameClient;
    private String descriptionComment;
    private Date dateComment;
    // Flag para a action bar de contexto
    public boolean selected;

        public Rating(String ad, String comment, Integer rating, Customer nameClient, String descriptionComment, Date dateComment) {
        this.ad = ad;
        this.comment = comment;
        this.rating = rating;
        this.nameClient = nameClient;
        this.descriptionComment = descriptionComment;
        this.dateComment = dateComment;
    }

    public Rating(){
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Customer getNameClient() {
        return nameClient;
    }

    public void setNameClient(Customer nameClient) {
        this.nameClient = nameClient;
    }

    public String getDescriptionComment() {
        return descriptionComment;
    }

    public void setDescriptionComment(String descriptionComment) {
        this.descriptionComment = descriptionComment;
    }

    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

}
