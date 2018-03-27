package com.example.liyuan.justgo.Model;


import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Created by Liyuan on 2018-03-25.
 */

public class searchModel implements Searchable{

    private String mTitle;

    public searchModel(String title) {
        mTitle = title;
    }

    public searchModel setTitle(String title) {
        mTitle = title;
        return this;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }
}
