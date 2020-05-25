package com.waoqi.msettopboxs.bean;

import java.util.List;

public class SearchLevelBean extends BasePresponce<List<SearchLevelBean>> {

    /**
     * id : 4
     * name : 妇产科疾病
     * creatTime : null
     * updateTime : null
     */

    private int id;
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
