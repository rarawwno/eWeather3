package com.eweather.eweather3;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.eweather.eweather3.controller.CitySpinnerController;
import com.eweather.eweather3.controller.CityWeatherController;
import com.eweather.eweather3.model.City;
import com.eweather.eweather3.model.CitySpinner;
import com.eweather.eweather3.model.CityWeather;
import com.eweather.eweather3.response.DistrictsList;
import com.eweather.eweather3.view.CitySpinnerView;
import com.eweather.eweather3.view.CityWeatherView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    /*MVC*/
    CityWeather cityWeather;
    CityWeatherController cityWeatherController;
    CitySpinner citySpinner;
    CitySpinnerController citySpinnerController;
    static City myCity;
    DistrictsList districtsList;
    /*控件*/
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RelativeLayout right_drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*ToolBar初始化*/
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        /*不显示toolBar标题*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        /*滑动布局初始化*/
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        right_drawerLayout = (RelativeLayout) findViewById(R.id.right_drawerLayout);
        /*MVC初始化*/
        citySpinner = new CitySpinner();
        cityWeather = new CityWeather();
        citySpinnerController = new CitySpinnerController(citySpinner, new CitySpinnerView());
        cityWeatherController = new CityWeatherController(cityWeather, new CityWeatherView());
        citySpinnerController.init(MainActivity.this);
        cityWeatherController.init(MainActivity.this);
        myCity=new City();
        /*设置点击监听*/
        right_drawerLayout.setOnClickListener(this);
        /*获取所有地区*/
        getAllLocation();
    }

    /*拼接请求地址和参数*/
    private String spliceAddress(String requestURL, HashMap<String, String> params) {
        StringBuilder url = new StringBuilder();
        url.append(requestURL).append("?");
        if (params != null && params.size() != 0) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                url.append(entry.getKey()).append("=").append(entry.getValue());
                url.append("&");
            }
            url.deleteCharAt(url.length() - 1);
        }
        return url.toString();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_city:
                Toast.makeText(MyApp.Context(), "请选择你的地址", Toast.LENGTH_SHORT).show();
                drawerLayout.openDrawer(GravityCompat.END);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_drawerLayout:
                drawerLayout.closeDrawer(GravityCompat.END);
                break;
            default:
                break;
        }
    }

    public void getAllLocation() {
        String url = "https://restapi.amap.com/v3/config/district";
        String key = "3b6e57cd1ef26be44b4c77c1c23f39f2";
        String subdistrict = "3";
        HashMap<String, String> params = new HashMap<>();
        params.put("key", key);
        params.put("subdistrict", subdistrict);
        url = spliceAddress(url, params);
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MyApp.Context(),"请求失败",Toast.LENGTH_SHORT);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();

                JsonObject responseObject = new JsonParser().parse(responseData).getAsJsonObject();
                Gson gson=new Gson();
                districtsList=gson.fromJson(responseObject,new TypeToken<DistrictsList>(){}.getType());
                DistrictsList.Country country=districtsList.getDistricts().get(0);
                /*获取所有省名list*/
                final ArrayList<String> provinceNameList=new ArrayList<>();
                final List<DistrictsList.Country.Province> provinceList=country.getDistricts();
                for(DistrictsList.Country.Province province:provinceList){
                    String provinceName=province.getName();
                    provinceNameList.add(provinceName);
                }
                /*加载省spinner*/
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        citySpinner.setProvinceList(provinceNameList);
                        /*适配省list*/
                        citySpinnerController.loadProvince(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                /*持久选中的provinceName*/
                                myCity.setProvinceName(provinceNameList.get(i));
                                /*遍历匹配市list*/
                                for(DistrictsList.Country.Province province:provinceList){
                                    if(province.getName().equals(myCity.getProvinceName())){
                                        final List<DistrictsList.Country.Province.City> cityList=province.getDistricts();
                                        final ArrayList<String> cityNameList=new ArrayList<>();
                                        for(DistrictsList.Country.Province.City city:cityList){
                                            String cityName=city.getName();
                                            cityNameList.add(cityName);
                                        }
                                        citySpinner.setCityList(cityNameList);
                                        citySpinnerController.loadCity(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                                /*持久选中的cityName*/
                                                myCity.setCityName(cityNameList.get(i));
                                                /*根据市名遍历市列表*/
                                                for(DistrictsList.Country.Province.City city:cityList){
                                                    /*if判断匹配的市名*/
                                                    if(myCity.getCityName().equals(city.getName())){
                                                        /*得到区列表*/
                                                        List<DistrictsList.Country.Province.City.County> countyList=city.getDistricts();
                                                        /*区列表转nameList*/
                                                        ArrayList<String> nameList=new ArrayList<>();
                                                        for(DistrictsList.Country.Province.City.County county:countyList){
                                                            String countyName=county.getName();
                                                            nameList.add(countyName);
                                                        }
                                                        /*持久区列表*/
                                                        citySpinner.setCountyList(nameList);
                                                        adaptCityList();
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
                });
            }
        });
    }
    /*适配区*/
    private void adaptCityList() {
        /*用控制器适配区list*/
        citySpinnerController.loadCounty(new AdapterView.OnItemSelectedListener() {
            /*设置区下拉选中事件*/
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                /*得到countyName*/
                for(String countyName:citySpinner.getCountyList()){
                    if(countyName.equals(citySpinner.getCountyList().get(i))){
                        /*持久countyName到City*/
                        myCity.setCountyName(countyName);
                        /*得到区adcode*/
                        getCountyAdcode();
                        /*更新视图*/
                        cityWeather.setCountyName(countyName);
                        cityWeatherController.loadCountyName();
                        updateWeather();
                        Toast.makeText(MyApp.Context(), "地址已经更改为:"+myCity.getProvinceName()+"-"+myCity.getCityName()+"-"+myCity.getCountyName(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    /*查询天气*/
    private void updateWeather() {
        String url="https://restapi.amap.com/v3/weather/weatherInfo";
        String key="3b6e57cd1ef26be44b4c77c1c23f39f2";
        String city=myCity.getAdcode();
        String extensions="base";
        HashMap<String,String> params=new HashMap<>();
        params.put("key",key);
        params.put("city",city);
        params.put("extensions",extensions);
        url=spliceAddress(url,params);
        /*天气请求*/
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(MyApp.Context(), "请求失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData=response.body().string();
                /*得到JsonObject*/
                JsonObject responseObject=new JsonParser().parse(responseData).getAsJsonObject();
                /*得到lives[0]*/
                JsonObject weatherObject=responseObject.getAsJsonArray("lives").get(0).getAsJsonObject();
                /*得到两个参数*/
                Gson gson=new Gson();
                String temperature=gson.fromJson(weatherObject.get("temperature"),new TypeToken<String>(){}.getType());
                String weather=gson.fromJson(weatherObject.get("weather"),new TypeToken<String>(){}.getType());
                cityWeather.setTemperature(temperature);
                cityWeather.setWeather(weather);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /*更新视图*/
                        cityWeatherController.loadTemperature();
                        cityWeatherController.loadWeather();
                    }
                });
            }
        });
    }

    /*得到区adcode*/
    private void getCountyAdcode() {
        /*得到CityModel
        * 得到provinceName*/
        String provinceName=myCity.getProvinceName();
        /*根据省遍历出市list*/
        List<DistrictsList.Country.Province> provinceList=districtsList.getDistricts().get(0).getDistricts();
        for(DistrictsList.Country.Province province:provinceList){
            /*if匹配对应省名*/
            if(province.getName().equals(provinceName)){
                /*得到cityList*/
                List<DistrictsList.Country.Province.City> cityList=province.getDistricts();
                /*根据市名遍历出区list*/
                for(DistrictsList.Country.Province.City city:cityList){
                    /*if匹配对应市名*/
                    if(city.getName().equals(myCity.getCityName())){
                        /*得到区list*/
                        List<DistrictsList.Country.Province.City.County> countyList=city.getDistricts();
                        /*根据区名遍历countyList*/
                        for(DistrictsList.Country.Province.City.County county:countyList){
                            /*if匹配对应区名*/
                            if(county.getName().equals(myCity.getCountyName())){
                                /*得到countyAdcode*/
                                myCity.setAdcode(city.getAdcode());
                                Log.d("tag",myCity.getAdcode());
                            }
                        }
                    }
                }
            }
        }
    }
}
