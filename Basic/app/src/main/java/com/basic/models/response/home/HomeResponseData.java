package com.basic.models.response.home;

import java.util.List;

/**
 * Created by ybk on 2015/9/7.
 */
public class HomeResponseData {
    private List<HomeResponseDataGoods> goods;

    public List<HomeResponseDataGoods> getGoods() {
        return goods;
    }

    public void setGoods(List<HomeResponseDataGoods> goods) {
        this.goods = goods;
    }
}
