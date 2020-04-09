package com.example.historian;


public class Listitem
{

    private String name;
    private String rating;
    private String Description;
    private String image_url;
//    private String museumdata;

    public Listitem() {
    }

    public Listitem(String name, String rating, String description, String image_url) {
        this.name = name;
        this.rating = rating;
        this.Description = description;
        this.image_url = image_url;
//        this.museumdata = museumdata;
    }

//    public String getMuseumdata() {
//        return museumdata;
//    }
//
//    public void setMuseumdata(String museumdata) {
//        this.museumdata = museumdata;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}





