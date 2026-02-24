package com.adrzdv.mtocp;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import com.adrzdv.mtocp.data.db.AppDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Deprecated
public class AppOld  {
    private static AppOld instance;
    private static AppDependencies appDependencies;
    private AppDatabase database;
    private static ExecutorService executor;

    public static AppOld getInstance() {
        return instance;
    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        instance = this;
//
//        try {
//            clearDataBase(this);
//        } catch (PackageManager.NameNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mtocpdb")
//                .createFromAsset("db/mtocpdb.db")
//                .build();
//
//        executor = Executors.newSingleThreadExecutor();
//        appDependencies = new AppDependencies(this, database, executor);
//
//    }

    public AppDatabase getDatabase() {
        return database;
    }

    public AppDependencies getAppDependencies() {
        return appDependencies;
    }

    private void clearDataBase(Context context) throws PackageManager.NameNotFoundException {
        SharedPreferences prefs = context.getSharedPreferences("app", Context.MODE_PRIVATE);

        String currVersion = context.getPackageManager()
                .getPackageInfo(context.getPackageName(), 0)
                .versionName;

        String savedVersion = prefs.getString("version", "");

        if (currVersion != null) {
            if (!currVersion.equals(savedVersion)) {
                context.deleteDatabase("mtocpdb");
                prefs.edit().putString("version", currVersion).apply();
            }
        }
    }
}
