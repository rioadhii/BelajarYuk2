package com.esaunggul.doayuk;

public class DetailDoa {
    private int sub_kategori_id;
    private String arab;
    private String latin;
    private String arti;

    public DetailDoa(int sub_kategori_id, String arab, String latin, String arti){
        this.sub_kategori_id = sub_kategori_id;
        this.arab = arab;
        this.latin = latin;
        this.arti = arti;
    }

    public DetailDoa(){

    }

    public int getSub_kategori_id() {
        return sub_kategori_id;
    }

    public void setSub_kategori_id(int sub_kategori_id) {
        this.sub_kategori_id = sub_kategori_id;
    }

    public String getArab() {
        return arab;
    }

    public void setArab(String arab) {
        this.arab = arab;
    }

    public String getLatin() {
        return latin;
    }

    public void setLatin(String latin) {
        this.latin = latin;
    }

    public String getArti() {
        return arti;
    }

    public void setArti(String arti) {
        this.arti = arti;
    }

}
