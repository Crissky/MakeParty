package com.inovaufrpe.makeparty.user.dominio;

import java.io.Serializable;

public class User implements Serializable {
    private String _id;
    private String email;
    private String password;


    public User(){}
    public User(String email, String senha){
        setEmail(email);
        setPassword(senha);
    }
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User[" +
                "_id='" + _id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ']';
    }
}
