package com.example.hp.recycleviewhorizontaldemo;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyApplication extends Application {
    private  static  VolleyApplication instance;
    private RequestQueue requestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        instance = this;
    }

    public synchronized static VolleyApplication getInstance() {
        return instance;
    }

    public com.android.volley.RequestQueue getRequestQueue() {
        return requestQueue;
    }

}
