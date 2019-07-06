package com.esaunggul.doayuk;

public class MateriList {
    private String Judul;
    private String Kategori;
    private String SpoiledKonten;
    private String KodeMateri;
    private int Thumbnail;
    private int Cover;
    private int SubKategori;

    public MateriList() {
    }

    public MateriList(String Judul, String Kategori, int Thumbnail, int Cover, String KodeMateri, String SpoiledKonten, int SubKategori) {
        this.Judul = Judul;
        this.Kategori = Kategori;
        this.Thumbnail = Thumbnail;
        this.Cover = Cover;
        this.KodeMateri = KodeMateri;
        this.SpoiledKonten = SpoiledKonten;
        this.SubKategori = SubKategori;
    }

    public String getSpoiledKonten() {
        return SpoiledKonten;
    }

    public void setSpoiledKonten(String SpoiledKonten) {
        this.SpoiledKonten = SpoiledKonten;
    }
    public String getJudul() {
        return Judul;
    }

    public void setJudul(String Judul) {
        this.Judul = Judul;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String Kategori) {
        this.Kategori = Kategori;
    }

    public String getKodeMateri() {
        return KodeMateri;
    }

    public void setKodeMateri(String KodeMateri) {
        this.KodeMateri = KodeMateri;
    }

    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int Thumbnail) {
        this.Thumbnail = Thumbnail;
    }

    public int getCover() {
        return Cover;
    }

    public void setCover(int Cover) {
        this.Cover = Cover;
    }

    public int getSubKategori() {
        return SubKategori;
    }

    public void setSubKategori(int SubKategori) {
        this.SubKategori = Cover;
    }
}
