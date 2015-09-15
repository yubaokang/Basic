package com.basic.models.response.city;

import com.basic.models.response.BaseResponse;

import java.util.List;

/**
 * Created by ybk on 2015/9/7.
 */
public class CityResponse extends BaseResponse {
    private List<CityResponseData> data;

    public List<CityResponseData> getData() {
        return data;
    }

    public void setData(List<CityResponseData> data) {
        this.data = data;
    }
}
