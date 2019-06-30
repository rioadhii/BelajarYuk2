package com.esaunggul.doayuk;

public class MateriList {
    private String Judul;
    private String Topik;
    private String Kategori;
    private String Konten;
    private String SpoiledKonten;
    private String KodeMateri;
    private int Thumbnail;
    private int Cover;
    public MateriList() {
    }

    public MateriList(String Judul, String Topik, String Kategori, String Konten, int Thumbnail, int Cover, String KodeMateri, String SpoiledKonten) {
        this.Judul = Judul;
        this.Topik = Topik;
        this.Kategori = Kategori;
        this.Konten = Konten;
        this.Thumbnail = Thumbnail;
        this.Cover = Cover;
        this.KodeMateri = KodeMateri;
        this.SpoiledKonten = SpoiledKonten;
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

    public String getTopik() {
        return Topik;
    }

    public void setTopik(String Topik) {
        this.Topik = Topik;
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
    public String getKonten() {
        return Konten;
    }

    public void setKonten(String Konten) {
        this.Konten = Konten;
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
}
