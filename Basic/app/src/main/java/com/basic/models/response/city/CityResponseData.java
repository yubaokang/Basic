package com.basic.models.response.city;

import java.io.Serializable;

/**
 * Created by hank on 2015/8/12.
 */

public class CityResponseData implements Serializable {

    private int id;

    private int is_enabel;

    private String name;//城市名

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIs_enabel() {
        return is_enabel;
    }

    public void setIs_enabel(int is_enabel) {
        this.is_enabel = is_enabel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
