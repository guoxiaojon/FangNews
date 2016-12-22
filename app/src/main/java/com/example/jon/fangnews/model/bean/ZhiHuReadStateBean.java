package com.example.jon.fangnews.model.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jon on 2016/12/8.
 */

public class ZhiHuReadStateBean  extends RealmObject{

    @PrimaryKey
    private int id;
    public void setId(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
