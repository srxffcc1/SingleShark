package com.businessframehelp.entity;

/**
 * Created by King6rf on 2017/8/2.
 */

public class WikiItem {
    private String item;

    public WikiItem(String item){
        this.item = item;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return item;
    }
}
