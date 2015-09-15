package com.basic.views.base;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by ybk on 2015/9/13.
 */
public class BasicApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        //Fresco基本用法，初始化
        Fresco.initialize(this);
    }
}
