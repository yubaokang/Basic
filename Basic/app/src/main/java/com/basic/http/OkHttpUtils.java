package com.basic.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.basic.utils.JsonUtil;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by ybk on 2015/9/5.
 * 使用OkHttp,网络访问，封装类
 */
public class OkHttpUtils {


    private String HOST_URL = "http://phoneapi.goujiawang.com/";

    private static OkHttpUtils okHttpUtils;

    private OkHttpClient client;

    private BaseResponse errBasic;

    private OnOkHttpListener onOkHttpListener;

    public interface OnOkHttpListener {
        void onSuccess(BaseResponse responseObject);

        void onFailed(BaseResponse responseObject);
    }

    private OkHttpUtils() {
        client = new OkHttpClient();
        errBasic = new BaseResponse();
        client.setConnectTimeout(15000, TimeUnit.SECONDS);
    }

    public static synchronized OkHttpUtils getInstance() {
        if (okHttpUtils == null)
            okHttpUtils = new OkHttpUtils();
        return okHttpUtils;
    }

    /**
     * @param url         get请求地址
     * @param responseClz 返回的对象的class
     * @param listener    回调接口
     */
    public <T> void get(String url, Class<T> responseClz, OnOkHttpListener listener) {
        Request request = new Request.Builder().url(HOST_URL + url).build();
        setResponse(request, responseClz, listener);
    }

    /**
     * @param url         post请求地址
     * @param baseRequest 请求的对象
     * @param responseClz 返回的对象的class
     * @param listener    回调接口
     */
    public <T> void post(String url, BaseRequest baseRequest, Class<T> responseClz, OnOkHttpListener listener) {
        RequestBody body = getBody(HOST_URL + url, baseRequest);
        Request request = new Request.Builder().url(HOST_URL + url).post(body).build();
        setResponse(request, responseClz, listener);
    }

    /**
     * 针对某些特殊的接口，请求的参数个数不固定，在javaBean中不好处理，
     * 可以在javaBean中写一个getRequestBody(),自定义需要返回的RequestBody对象
     * 比如：筛选接口，
     *
     * @param url         post请求地址
     * @param requestBody 请求的RequestBody对象
     * @param responseClz 返回的对象的class
     * @param listener    回调接口
     */
    public <T> void post(String url, RequestBody requestBody, Class<T> responseClz, OnOkHttpListener listener) {
        Request request = new Request.Builder().url(HOST_URL + url).post(requestBody).build();
        setResponse(request, responseClz, listener);
    }


    /**
     * @param request     OkHttp中的Request对象
     * @param responseClz 接口请求返回的json需要转换成的Object对象
     * @param listener    回调接口
     */
    private <T> void setResponse(Request request, final Class<T> responseClz, OnOkHttpListener listener) {
        onOkHttpListener = listener;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                onOkHttpListener.onFailed(getErrResponse());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Log.i(">>OkHttp--返回     ", json);
                BaseResponse baseResponse = (BaseResponse) JsonUtil.getObj(json, responseClz);
                if (baseResponse != null) {
                    //basicResponse.setMsg("请求成功");
                    //basicResponse.setReturnCode(200);
                    onOkHttpListener.onSuccess(baseResponse);
                } else {
                    onOkHttpListener.onFailed(getErrResponse());
                }
            }
        });
    }

    /**
     * @param baseRequest 将对象转换成BasicResponse
     * @return RequestParams
     */
    public RequestBody getBody(String url, BaseRequest baseRequest) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        StringBuilder sb = new StringBuilder();
        sb.append(url).append("?");
        if (baseRequest != null) {
            Map<String, String> map = JsonUtil.getMapStr(JSON.toJSONString(baseRequest));
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                sb.append(key).append("=").append(value).append("&");
                builder.add(key.toString(), value.toString());
            }
            Log.i(">>>>>OkHttp--请求>>>>", sb.toString());
        }
        return builder.build();
    }

    /**
     * @return ResponseBasic 网络错误，返回带有错误信息的ResponseBasic对象
     */
    private BaseResponse getErrResponse() {
        errBasic.setMsg("网络错误");
        errBasic.setReturnCode(404);
        return errBasic;
    }
}
