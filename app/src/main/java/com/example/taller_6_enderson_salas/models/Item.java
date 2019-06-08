package com.example.taller_6_enderson_salas.models;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Item implements Serializable {
    public String id;
    public String title;
    public String subTitle;

    public Item(String title, String subTitle,String id) {
        this.title = title;
        this.subTitle = subTitle;
        this.id = id;
    }

    public Item(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public Item() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subtitle) {
        this.subTitle = subtitle;
    }
}
