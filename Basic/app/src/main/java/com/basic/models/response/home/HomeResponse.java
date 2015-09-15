package com.basic.models.response.home;

import com.basic.models.response.BaseResponse;

/**
 * Created by ybk on 2015/9/7.
 */
public class HomeResponse extends BaseResponse {
    private HomeResponseData data;

    public HomeResponseData getData() {
        return data;
    }

    public void setData(HomeResponseData data) {
        this.data = data;
    }
}
