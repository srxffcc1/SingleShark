package com.shark.app.business.entity;

/**
 * Created by King6rf on 2017/6/2.
 */

public class ImageMenuEntity {
    public Class clickpassclass;
    public int imageRid;

    public Class getClickpassclass() {
        return clickpassclass;
    }

    public ImageMenuEntity setClickpassclass(Class clickpassclass) {
        this.clickpassclass = clickpassclass;
        return  this;
    }

    public int getImageRid() {
        return imageRid;
    }

    public ImageMenuEntity setImageRid(int imageRid) {
        this.imageRid = imageRid;
        return  this;
    }
}
