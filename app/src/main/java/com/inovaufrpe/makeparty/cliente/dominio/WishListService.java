package com.inovaufrpe.makeparty.cliente.dominio;

import com.inovaufrpe.makeparty.infra.SessaoApplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WishListService {
    static final String URL_BASE = "https://makepartyserver.herokuapp.com/wishlists?token="+SessaoApplication.getInstance().getTokenUser();

    @GET("")
    Call <Wishlists> wishListUser();

}
