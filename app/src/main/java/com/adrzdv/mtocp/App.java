package com.adrzdv.mtocp;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import com.adrzdv.mtocp.data.db.AppDatabase;
import com.adrzdv.mtocp.data.repository.ViolationRepositoryImpl;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;

public class App extends Application {
    private static App instance;
    private static Toast currentToast;

    private AppDatabase database;
    private ViolationRepository violationRepository;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        instance = this;

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "mtocpdb")
                .createFromAsset("db/mtocpdb.db")
                .build();

        violationRepository = new ViolationRepositoryImpl(database.violationDao());
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public ViolationRepository getViolationRepository() {
        return violationRepository;
    }

    public static void showToast(Context context, String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        currentToast.show();
    }
}
