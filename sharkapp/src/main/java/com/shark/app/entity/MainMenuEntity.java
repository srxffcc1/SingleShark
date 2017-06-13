package com.shark.app.entity;

/**
 * Created by King6rf on 2017/6/2.
 */

public class MainMenuEntity {
    public Class clickpassclass;
    public int imageRid;

    public Class getClickpassclass() {
        return clickpassclass;
    }

    public MainMenuEntity setClickpassclass(Class clickpassclass) {
        this.clickpassclass = clickpassclass;
        return  this;
    }

    public int getImageRid() {
        return imageRid;
    }

    public MainMenuEntity setImageRid(int imageRid) {
        this.imageRid = imageRid;
        return  this;
    }
}
