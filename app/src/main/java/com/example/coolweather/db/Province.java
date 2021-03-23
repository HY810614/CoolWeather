package com.example.coolweather.db;

import org.litepal.crud.LitePalSupport;
//LitePal的每一个实体类必须继承自LitePalSupport
public class Province extends LitePalSupport {
    private int id; //每个实体类都应该有id

    private String provinceName; //省名

    private int provinceCode; //省的代号

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
