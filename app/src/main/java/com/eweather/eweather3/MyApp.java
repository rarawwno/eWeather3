package com.eweather.eweather3;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
    public static Context Context(){
        return context;
    }
}
