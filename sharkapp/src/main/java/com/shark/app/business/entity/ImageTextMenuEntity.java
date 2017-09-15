package com.shark.app.business.entity;

import android.content.Intent;

/**
 * Created by King6rf on 2017/6/2.
 */

public class ImageTextMenuEntity {
    public Class clickpassclass;
    public int imageRid;
    public String name;
    public Intent intent;

    public ImageTextMenuEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ImageTextMenuEntity setName(String name) {
        this.name = name;
        return  this;
    }

    public Class getClickpassclass() {
        return clickpassclass;
    }

    public ImageTextMenuEntity setClickpassclass(Class clickpassclass) {
        this.clickpassclass = clickpassclass;
        return  this;
    }

    public int getImageRid() {
        return imageRid;
    }

    public ImageTextMenuEntity setImageRid(int imageRid) {
        this.imageRid = imageRid;
        return  this;
    }

    public ImageTextMenuEntity setIntent(Intent intent) {
        this.intent = intent;
        return this;
    }

    public Intent getIntent() {
        return intent;
    }
}
