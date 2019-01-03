package com.inovaufrpe.makeparty.fornecedor.dominio;

public class Plano {

    private String name;
    private int totalad;
    private int totalphoto;


    public String getType() {
        return name;
    }

    public void setType(String name) {
        this.name = name;
    }

    public int getTotalad() {
        return totalad;
    }

    public void setTotalad(int totalad) {
        this.totalad = totalad;
    }

    public int getTotalphoto() {
        return totalphoto;
    }

    public void setTotalphoto(int totalphoto) {
        this.totalphoto = totalphoto;
    }


    @Override
    public String toString() {
        return "Plano[" +
                "type='" + name + '\'' +
                ", totalad=" + totalad +
                ", totalphoto=" + totalphoto +
                ']';
    }
}
