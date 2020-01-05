package com.geetol.zimu.bean;

public class BannerData {
    private String img;
    private int rImg;
    private String url;
    private String name;
    private boolean isMovie;

    public BannerData(String img,String url, String name, boolean isMovie) {
        this.url = url;
        this.img = img;
        this.name = name;
        this.isMovie = isMovie;
    }

    public BannerData(int rImg,String url, String name, boolean isMovie) {
        this.url = url;
        this.rImg = rImg;
        this.name = name;
        this.isMovie = isMovie;
    }

    public boolean isMovie() {
        return isMovie;
    }

    public void setMovie(boolean movie) {
        isMovie = movie;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getrImg() {
        return rImg;
    }

    public void setrImg(int rImg) {
        this.rImg = rImg;
    }
}
