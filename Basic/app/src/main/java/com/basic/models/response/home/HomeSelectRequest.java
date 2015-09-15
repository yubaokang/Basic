package com.basic.models.response.home;

import com.basic.models.request.BaseRequest;

/**
 * Created by ybk on 2015/9/7.
 */
public class HomeSelectRequest extends BaseRequest {
    private int cityId;//城市ID
    private int styleId;//系列ID
    private int houseTypeId;//户型ID
    private int minPrice;//价格最小值
    private int maxPrice;//价格最大值
    private int minArea;//面积最小值
    private int maxArea;//面积最大值
    private String q;//搜索条件
    private int pageNum = 1;//页数

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getStyleId() {
        return styleId;
    }

    public void setStyleId(int styleId) {
        this.styleId = styleId;
    }

    public int getHouseTypeId() {
        return houseTypeId;
    }

    public void setHouseTypeId(int houseTypeId) {
        this.houseTypeId = houseTypeId;
    }

    public int getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getMinArea() {
        return minArea;
    }

    public void setMinArea(int minArea) {
        this.minArea = minArea;
    }

    public int getMaxArea() {
        return maxArea;
    }

    public void setMaxArea(int maxArea) {
        this.maxArea = maxArea;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
