package com.example.coolweather.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpUtil {
    public static void sendOkHttpRequest(String address, okhttp3.Callback callback) {
        //client实例
        OkHttpClient client = new OkHttpClient();
        //向服务器发送请求
        Request request = new Request.Builder().url(address).build();
        //异步GET使用
        client.newCall(request).enqueue(callback);
    }
}
