package com.radhio.parent;

import android.content.Context;

import com.google.android.play.core.splitcompat.SplitCompat;

/**
 * Created by Azmia Hoque Radhio on 9/14/2021.
 */
public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        SplitCompat.install(this);
    }
}
