package com.example.coolweather.db;

import org.litepal.crud.LitePalSupport;

public class County extends LitePalSupport {
    private int id;

    private String countyName;

    private String weaterId; //记录县所对应的天气id

    private int cityId; //当前县所属市id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeaterId() {
        return weaterId;
    }

    public void setWeaterId(String weaterId) {
        this.weaterId = weaterId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
