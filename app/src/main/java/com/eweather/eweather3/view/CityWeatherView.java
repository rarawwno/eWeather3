package com.eweather.eweather3.view;

import android.widget.TextView;

import com.eweather.eweather3.MainActivity;
import com.eweather.eweather3.R;
import com.eweather.eweather3.model.CityWeather;

public class CityWeatherView {
    TextView temperature;
    TextView weather;
    TextView county_name;

    public void init(MainActivity activity){
        temperature=(TextView)activity.findViewById(R.id.temperature);
        weather=(TextView)activity.findViewById(R.id.weather);
        county_name=(TextView)activity.findViewById(R.id.county_name);
    }
    public void loadCountyName(String countyName){
        county_name.setText(countyName);
    }
    public void loadWeather(String weatherStatus){
        weather.setText(weatherStatus);
    }
    public void loadTemperature(String temperatureNum){
        temperature.setText(temperatureNum+"℃");
    }
}
