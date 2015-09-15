package com.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.basic.city.CityResponse;
import com.basic.home.HomeResponseData;
import com.basic.home.HomeResponseDataGoods;
import com.basic.home.HomeResponse;
import com.basic.http.BaseResponse;
import com.basic.http.OkHttpUtils;
import com.basic.home.HomeSelectRequest;
import com.googlecode.androidannotations.annotations.ViewById;

public class MainActivity extends AppCompatActivity {
    public static final String GET_CITY_LIST = "area/getCityList.html";

    String uuu = "goods/getGoodsListCondition.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        query();
        queryJHome();
    }

    private void query() {
        OkHttpUtils.getInstance().get(GET_CITY_LIST, CityResponse.class, new OkHttpUtils.OnOkHttpListener() {
            @Override
            public void onSuccess(BaseResponse responseObject) {
                CityResponse cityResponse = (CityResponse) responseObject;
                Log.i("----111-", JSON.toJSONString(cityResponse));
            }

            @Override
            public void onFailed(BaseResponse responseObject) {

            }
        });
    }

    private void queryJHome() {
        HomeSelectRequest select = new HomeSelectRequest();
        select.setCityId(1213);
        select.setMaxPrice(100);
        select.setPageNum(2);
        select.setQ("杭州");

        OkHttpUtils.getInstance().post(uuu, select, HomeResponse.class, new OkHttpUtils.OnOkHttpListener() {
            @Override
            public void onSuccess(BaseResponse responseObject) {
                HomeResponse homeResponse = (HomeResponse) responseObject;
                HomeResponseData homeResponseData = homeResponse.getData();
                HomeResponseDataGoods homeResponseDataGoods = homeResponseData.getGoods().get(0);
                Log.i("----解析之后--", JSON.toJSONString(homeResponseDataGoods));
            }

            @Override
            public void onFailed(BaseResponse responseObject) {

            }
        });
    }
}
