package com.eweather.eweather3.model;

import java.util.ArrayList;

public class CitySpinner {
    ArrayList<String> provinceList;
    ArrayList<String> cityList;
    ArrayList<String> countyList;

    public ArrayList<String> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(ArrayList<String> provinceList) {
        this.provinceList = provinceList;
    }

    public ArrayList<String> getCityList() {
        return cityList;
    }

    public void setCityList(ArrayList<String> cityList) {
        this.cityList = cityList;
    }

    public ArrayList<String> getCountyList() {
        return countyList;
    }

    public void setCountyList(ArrayList<String> countyList) {
        this.countyList = countyList;
    }
}
