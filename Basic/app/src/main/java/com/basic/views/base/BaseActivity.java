package com.basic.views.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.basic.http.OkHttpUtils;
import com.basic.models.response.BaseResponse;

public class BaseActivity extends AppCompatActivity implements OkHttpUtils.OnOkHttpListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onOkResponse(BaseResponse responseObject) {

    }
}
