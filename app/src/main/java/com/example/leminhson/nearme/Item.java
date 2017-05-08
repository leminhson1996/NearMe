package com.example.leminhson.nearme;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by leminhson on 5/8/17.
 */

public class Item{
    private String title;
    private String counter;
    private String imageLink;
    private String phone;
    private LatLng position;

    public Item(String title){
        this.title = title;
    }

    public Item(String imageLink, String title, String counter) {
        super();
        this.imageLink = imageLink;
        this.title = title;
        this.counter = counter;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImageLink() {
        return imageLink;
    }
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
    public String getCounter() {
        return counter;
    }
    public void setCounter(String counter) {
        this.counter = counter;
    }
    public void setPosition(LatLng position){this.position = position;}
    public LatLng getPosition(){return position;}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
