package com.example.examaster;

public class Movie {

    private final String title;
    private final String imageurl;
    private final String subtitle;
    private final String imageurl2;
    private final String otherurl;


    public Movie(String title, String imageurl, String subtitle, String imageurl2, String otherurl){
        this.title = title;
        this.imageurl = imageurl;
        this.subtitle = subtitle;
        this.imageurl2 = imageurl2;
        this.otherurl = otherurl;

    }

    public String getTitle() {
        return title;
    }

    public String getImageurl() {
        return imageurl;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getImageurl2() {
        return imageurl2;
    }

    public String getOtherurl() {
        return otherurl;
    }
}
