package com.esaunggul.doayuk;

public class SubKategori {
    private int kategori_id;
    private int sub_kategori_id;
    private String kategori_name;

    public SubKategori(int kategori_id, int sub_kategori_id, String kategori_name){
        this.kategori_id = kategori_id;
        this.sub_kategori_id = sub_kategori_id;
        this.kategori_name = kategori_name;
    }

    public SubKategori(){

    }

    public int getKategori_id() {
        return kategori_id;
    }

    public void setKategori_id(int kategori_id) {
        this.kategori_id = kategori_id;
    }

    public int getSub_kategori_id() {
        return sub_kategori_id;
    }

    public void setSub_kategori_id(int sub_kategori_id) {
        this.sub_kategori_id = sub_kategori_id;
    }

    public String getKategori_name() {
        return kategori_name;
    }

    public void setKategori_name(String kategori_name) {
        this.kategori_name = kategori_name;
    }

}
