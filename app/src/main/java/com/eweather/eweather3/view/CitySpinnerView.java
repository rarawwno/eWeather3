package com.eweather.eweather3.view;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.eweather.eweather3.MainActivity;
import com.eweather.eweather3.MyApp;
import com.eweather.eweather3.R;
import java.util.ArrayList;

public class CitySpinnerView {
    Spinner province_spinner;
    Spinner city_spinner;
    Spinner county_spinner;
    ArrayAdapter<String> adapter;
    public void init(MainActivity activity){
        province_spinner=(Spinner)activity.findViewById(R.id.province_spinner);
        city_spinner=(Spinner)activity.findViewById(R.id.city_spinner);
        county_spinner=(Spinner)activity.findViewById(R.id.county_spinner);
    }
    /*载入省list*/
    public void loadProvince(final ArrayList<String> list, AdapterView.OnItemSelectedListener listener){
        adapter=new ArrayAdapter<String>(MyApp.Context(),R.layout.spinner_item,list);
        province_spinner.setAdapter(adapter);
        province_spinner.setOnItemSelectedListener(listener);
    }
    /*载入市list*/
    public void loadCity(final ArrayList<String> list, AdapterView.OnItemSelectedListener listener){
        adapter=new ArrayAdapter<String>(MyApp.Context(),R.layout.spinner_item,list);
        city_spinner.setAdapter(adapter);
        city_spinner.setOnItemSelectedListener(listener);
    }
    /*载入区list*/
    public void loadCounty(ArrayList<String> list, AdapterView.OnItemSelectedListener listener){
        adapter=new ArrayAdapter<String>(MyApp.Context(),R.layout.spinner_item,list);
        county_spinner.setAdapter(adapter);
        county_spinner.setOnItemSelectedListener(listener);
    }
}
