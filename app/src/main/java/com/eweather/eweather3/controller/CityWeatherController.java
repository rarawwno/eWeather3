package com.eweather.eweather3.controller;

import com.eweather.eweather3.MainActivity;
import com.eweather.eweather3.model.CityWeather;
import com.eweather.eweather3.view.CityWeatherView;

public class CityWeatherController {
    CityWeather model;
    CityWeatherView view;

    public CityWeatherController() {
    }

    public CityWeatherController(CityWeather model, CityWeatherView view) {
        this.model = model;
        this.view = view;
    }

    public CityWeather getModel() {
        return model;
    }

    public void setModel(CityWeather model) {
        this.model = model;
    }

    public CityWeatherView getView() {
        return view;
    }

    public void setView(CityWeatherView view) {
        this.view = view;
    }

    public void loadCountyName(){
        view.loadCountyName(model.getCountyName());
    }
    public void loadWeather(){
        view.loadWeather(model.getWeather());
    }
    public void loadTemperature(){
        view.loadTemperature(model.getTemperature());
    }
    public void init(MainActivity activity){
        view.init(activity);
    }
}
