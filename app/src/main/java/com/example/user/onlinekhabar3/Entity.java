package com.example.user.onlinekhabar3;

import android.content.Intent;

import java.io.Serializable;

/**
 * Created by user on 7/29/2016.
 */
public class Entity implements Serializable {
    private int id;
    private String name ;
    private String image ;
    private String date;
    private String comment;
    private int category_id;
    private String event_title;
    public  Entity(int id, String name, String image, String date, String comment,int category_id,String event_title){
        this.id = id;
        this.name = name;
        this.image = image;
        this.date = date;
        this.id = id;
        this.comment = comment;
        this.category_id=category_id;
        this.event_title=event_title;


    }

    public String getEvent_title() {
        return event_title;
    }

    public void setEvent_title(String event_title) {
        this.event_title = event_title;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getImage(){return image;}
    public void setImage(String image){this.image=image;}
    public String getDate(){return date;}
    public void setDate(String date ){this.date=date;}
    public String getComment(){return comment;}
    public void setComment(String comment ){this.comment=comment;}








}

