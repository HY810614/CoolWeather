package com.example.coolweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.coolweather.db.City;
import com.example.coolweather.db.County;
import com.example.coolweather.db.Province;
import com.example.coolweather.gson.Weather;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    private static final String TAG = "Utility";
    
    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                //创建一个JSON数组存放返回的数据
                JSONArray allProvinces = new JSONArray(response);
                //遍历JSONObject对象
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    //获取 省表 的实例
                    Province province = new Province();
                    //向表中添加name 和 code
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    //完成对数据的添加
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
        /**
         * 解析和处理服务器返回的县级数据
         */
        public static boolean handleCountyResponse (String response,int cityId){
            if (!TextUtils.isEmpty(response)) {
                try {
                    JSONArray allCounties = new JSONArray(response);
                    for (int i = 0; i < allCounties.length(); i++) {
                        JSONObject countyObject = allCounties.getJSONObject(i);
                        County county = new County();
                        county.setCountyName(countyObject.getString("name"));
                        county.setWeatherId(countyObject.getString("weather_id"));
                        county.setCityId(cityId);
                        county.save();
                    }
                    return true;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

    
}

