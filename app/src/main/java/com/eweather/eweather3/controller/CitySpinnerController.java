package com.eweather.eweather3.controller;

import android.widget.AdapterView;

import com.eweather.eweather3.MainActivity;
import com.eweather.eweather3.model.CitySpinner;
import com.eweather.eweather3.view.CitySpinnerView;

import java.util.ArrayList;

public class CitySpinnerController {
    CitySpinner model;
    CitySpinnerView view;

    public CitySpinnerController() {
    }

    public CitySpinnerController(CitySpinner model, CitySpinnerView view) {
        this.model = model;
        this.view = view;
    }

    public CitySpinner getModel() {
        return model;
    }

    public void setModel(CitySpinner model) {
        this.model = model;
    }

    public CitySpinnerView getView() {
        return view;
    }

    public void setView(CitySpinnerView view) {
        this.view = view;
    }

    public void init(MainActivity activity){
        view.init(activity);
    }
    public void loadProvince(AdapterView.OnItemSelectedListener listener){
        view.loadProvince(model.getProvinceList(),listener);
    }
    public void loadCity(AdapterView.OnItemSelectedListener listener){
        view.loadCity(model.getCityList(),listener);
    }
    public void loadCounty(AdapterView.OnItemSelectedListener listener){
        view.loadCounty(model.getCountyList(),listener);
    }
}
