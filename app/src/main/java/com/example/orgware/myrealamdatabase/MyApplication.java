package com.example.orgware.myrealamdatabase;

import android.app.Application;

import com.squareup.otto.Bus;

/**
 * Created by Orgware on 8/2/2017.
 */

public class MyApplication extends Application {

    public static Bus bus;
    public static MyApplication INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }

    public static MyApplication getInstance() {
        return INSTANCE;
    }

    public Bus getBusInstance() {
        if (bus == null)
            bus = new Bus();
        return bus;

    }
}
