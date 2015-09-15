package com.basic.http;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.basic.consts.UrlConst;
import com.basic.models.request.BaseRequest;
import com.basic.models.response.BaseResponse;
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

    private String HOST_URL = UrlConst.URL_SERVICES;

    private static OkHttpUtils okHttpUtils;

    private OkHttpClient client;

    private BaseResponse errBasic;

    private OnOkHttpListener onOkHttpListener;

    private Handler handler;

    public interface OnOkHttpListener {
        void onOkResponse(BaseResponse responseObject);
    }

    private OkHttpUtils() {
        HOST_URL = UrlConst.URL_SERVICES;
        client = new OkHttpClient();
        errBasic = new BaseResponse();
        client.setConnectTimeout(15000, TimeUnit.SECONDS);
        handler = new Handler();
    }

    public static synchronized OkHttpUtils getInstance() {
        if (okHttpUtils == null)
            okHttpUtils = new OkHttpUtils();
        return okHttpUtils;
    }
    /////////////////////////////////////////////////////////
    ////以下方式采用匿名内部类回调接口
    /////////////////////////////////////////////////////////

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
     * @param url         post请求地址
     * @param requestBody 请求的requestBody
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
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onOkHttpListener.onOkResponse(getErrResponse());
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Log.i("okhttp--返回     ", json);
                final BaseResponse baseResponse = (BaseResponse) JsonUtil.getObj(json, responseClz);
                if (baseResponse != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onOkHttpListener.onOkResponse(baseResponse);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onOkHttpListener.onOkResponse(getErrResponse());
                        }
                    });
                }
            }
        });
    }

    /**
     * @return ResponseBasic 网络错误，返回带有错误信息的ResponseBasic对象
     */
    private BaseResponse getErrResponse() {
        errBasic.setMsg("网络错误");
        errBasic.setReturnCode(404);
        return errBasic;
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
                if (!TextUtils.isEmpty(value.toString())) {
                    sb.append(key).append("=").append(value).append("&");
                    builder.add(key.toString(), value.toString());
                }
            }
            Log.i("okhttp--请求>>>>", sb.toString().substring(0, sb.toString().length() - 1));
        }
        return builder.build();
    }

    /////////////////////////////////////////////////////////
    ////以下可以根据传递进来的接口号(tasktype)，判断是哪个接口返回，在最外部写一个接口回调
    /////////////////////////////////////////////////////////

    /**
     * @param tasktype    接口号
     * @param url         get请求地址
     * @param responseClz 返回的对象的class
     * @param listener    回调接口
     */
    public <T> void get(int tasktype, String url, Class<T> responseClz, OnOkHttpListener listener) {
        Request request = new Request.Builder().url(HOST_URL + url).build();
        setResponse(tasktype, request, responseClz, listener);
    }

    /**
     * @param tasktype    接口号
     * @param url         post请求地址
     * @param baseRequest 请求的对象
     * @param responseClz 返回的对象的class
     * @param listener    回调接口
     */
    public <T> void post(int tasktype, String url, BaseRequest baseRequest, Class<T> responseClz, OnOkHttpListener listener) {
        RequestBody body = getBody(HOST_URL + url, baseRequest);
        Request request = new Request.Builder().url(HOST_URL + url).post(body).build();
        setResponse(tasktype, request, responseClz, listener);
    }

    /**
     * @param tasktype    接口号
     * @param url         post请求地址
     * @param requestBody 请求的requestBody
     * @param responseClz 返回的对象的class
     * @param listener    回调接口
     */
    public <T> void post(int tasktype, String url, RequestBody requestBody, Class<T> responseClz, OnOkHttpListener listener) {
        Request request = new Request.Builder().url(HOST_URL + url).post(requestBody).build();
        setResponse(tasktype, request, responseClz, listener);
    }

    /**
     * @param tasktype    接口号
     * @param request     OkHttp中的Request对象
     * @param responseClz 接口请求返回的json需要转换成的Object对象
     * @param listener    回调接口
     */
    private <T> void setResponse(final int tasktype, Request request, final Class<T> responseClz, OnOkHttpListener listener) {
        onOkHttpListener = listener;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onOkHttpListener.onOkResponse(getErrResponse(tasktype));
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String json = response.body().string();
                Log.i(">>okhttp--返回     ", json);
                final BaseResponse baseResponse = (BaseResponse) JsonUtil.getObj(json, responseClz);
                if (baseResponse != null) {
                    baseResponse.setTaskType(tasktype);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onOkHttpListener.onOkResponse(baseResponse);
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            onOkHttpListener.onOkResponse(getErrResponse(tasktype));
                        }
                    });
                }
            }
        });
    }

    /**
     * @return ResponseBasic 网络错误，返回带有错误信息的ResponseBasic对象
     */
    private BaseResponse getErrResponse(int taskType) {
        errBasic.setMsg("网络错误");
        errBasic.setReturnCode(404);
        errBasic.setTaskType(taskType);
        return errBasic;
    }

}