package com.example.user.onlinekhabar3;

import java.io.Serializable;

/**
 * Created by user on 7/29/2016.
 */
public class Entity1 implements Serializable {
    private int event_id;
    private int id;
    private String name ;
    private String comment;

    public Entity1(int event_id,int id, String name, String comment){
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.event_id=event_id;



    }

    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName(){return name;}
    public void setName(String name){this.name=name;}
    public String getComment(){return comment;}
    public void setComment(String comment ){this.comment=comment;}








}

