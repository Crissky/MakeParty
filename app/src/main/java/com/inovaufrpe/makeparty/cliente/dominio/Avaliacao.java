package com.inovaufrpe.makeparty.cliente.dominio;

import com.inovaufrpe.makeparty.fornecedor.dominio.Ads;

import java.util.Date;

public class Avaliacao {

    private PessoaFisica socialname;
    private Ads ad;
    private String descriptionComment;
    private Double ratingUser;
    private Date dateComment;

    public PessoaFisica getSocialname() {
        return socialname;
    }

    public void setSocialname(PessoaFisica socialname) {
        this.socialname = socialname;
    }

    public Ads getAd() {
        return ad;
    }

    public void setAd(Ads ad) {
        this.ad = ad;
    }

    public String getDescriptionComment() {
        return descriptionComment;
    }

    public void setDescriptionComment(String descriptionComment) {
        this.descriptionComment = descriptionComment;
    }

    public Double getRatingUser() {
        return ratingUser;
    }

    public void setRatingUser(Double ratingUser) {
        this.ratingUser = ratingUser;
    }

    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }

}
