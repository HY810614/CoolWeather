package com.example.coolweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.coolweather.db.City;
import com.google.gson.Gson;
import com.qweather.sdk.bean.base.Code;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;
import com.qweather.sdk.view.HeConfig;
import com.qweather.sdk.view.QWeather;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String CityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HeConfig.init("HE2103242125441064","051e17cd7bdb4ace87585806e0233f8f");
        HeConfig.switchToDevService();

//        QWeather.getGeoCityLookup(this, "北京", new QWeather.OnResultGeoListener() {
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onSuccess(GeoBean geoBean) {
//                List<GeoBean.LocationBean> LocationBeans = geoBean.getLocationBean();
//                CityId = LocationBeans.get(0).getId();
//                Log.d(TAG, "LocationID:" + CityId);
//            }
//        });

//        QWeather.getWeatherNow(MainActivity.this,CityId,new QWeather.OnResultWeatherNowListener() {
//
//            @Override
//            public void onError(Throwable throwable) {
//                Log.d(TAG, "onSuccess: 2");
//                Log.d(TAG, "getWeather onError: " + throwable);
//            }

//            @Override
//            public void onSuccess(WeatherNowBean weatherNowBean) {
//                Log.d(TAG, "onSuccess: 1");
//                Log.i(TAG, "getWeather onSuccess: " + new Gson().toJson(weatherNowBean));
//                //先判断返回的status是否正确，当status正确时获取数据，若status不正确，可查看status对应的Code值找到原因
//                if (Code.OK.getCode().equalsIgnoreCase(String.valueOf(weatherNowBean.getCode()))) {
//                    WeatherNowBean.NowBaseBean now = weatherNowBean.getNow();
//                    Log.d(TAG, "天气情况: " + now.getText());
//                } else {
//                    //在此查看返回数据失败的原因
//                    Code status = weatherNowBean.getCode();
//                    Code code = Code.toEnum(String.valueOf(status));
//                    Log.d(TAG, "failed code: " + code);
//                }
//            }
//        });
        }

}