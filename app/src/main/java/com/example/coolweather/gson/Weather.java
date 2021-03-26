package com.example.coolweather.gson;

public class Weather {

    //更新时间
    public String updateTime;
    //云的量
    public String cloud;
    //天气状况
    public String text;
    //风向
    public String windDir;
    //风力
    public String windScale;
    //压强
    public String press;
    //温度
    public String temp;
    //相对湿度
    public String humidity;
    //能见度 单位公里
    public String vis;
    //降水量
    public String precip;
    //体感温度
    public String feelsLike;


    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getPrecip() {
        return precip;
    }

    public void setPrecip(String precip) {
        this.precip = precip;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindScale() {
        return windScale;
    }

    public void setWindScale(String windScale) {
        this.windScale = windScale;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCloud() {
        return cloud;
    }

    public void setCloud(String cloud) {
        this.cloud = cloud;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        String day = updateTime.substring(0,updateTime.indexOf("T"));
        String time = updateTime.substring(updateTime.indexOf("T")+1,updateTime.indexOf("+"));
        this.updateTime = day + " " + time;
    }

}
