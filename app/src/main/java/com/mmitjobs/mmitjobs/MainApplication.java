package com.mmitjobs.mmitjobs;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.stetho.Stetho;
import com.mmitjobs.mmitjobs.util.TypefaceManager;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainApplication extends Application {

    public static Realm realm;
    public static TypefaceManager typefaceManager;
    protected static MainApplication instance;

    public MainApplication() {
        super();
        instance = this;
    }

    public static MainApplication get() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(getApplicationContext())
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
        realm = Realm.getDefaultInstance();

        Fresco.initialize(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
