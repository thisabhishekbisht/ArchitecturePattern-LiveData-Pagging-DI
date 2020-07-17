package com.abhishek.architecturepattern_livedata_pagging_di;

import android.app.Application;
import android.content.Context;

import com.abhishek.architecturepattern_livedata_pagging_di.di.AppComponent;
import com.abhishek.architecturepattern_livedata_pagging_di.di.DaggerAppComponent;
import com.abhishek.architecturepattern_livedata_pagging_di.di.UtilsModule;
import com.facebook.drawee.backends.pipeline.Fresco;



public class MyApplication extends Application {
    AppComponent appComponent;
    Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        appComponent = DaggerAppComponent.builder().utilsModule(new UtilsModule()).build();
        Fresco.initialize(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }
}
