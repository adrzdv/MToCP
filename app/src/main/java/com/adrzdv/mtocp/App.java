package com.adrzdv.mtocp;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import com.adrzdv.mtocp.data.db.AppDatabase;
import com.adrzdv.mtocp.data.importmodel.CompanyImport;
import com.adrzdv.mtocp.data.importmodel.DepotImport;
import com.adrzdv.mtocp.data.importmodel.TrainImport;
import com.adrzdv.mtocp.data.importmodel.ViolationImport;
import com.adrzdv.mtocp.data.repository.CompanyRepositoryImpl;
import com.adrzdv.mtocp.data.repository.DepotRepositoryImpl;
import com.adrzdv.mtocp.data.repository.TrainRepositoryImpl;
import com.adrzdv.mtocp.data.repository.ViolationRepositoryImpl;
import com.adrzdv.mtocp.domain.repository.CompanyRepository;
import com.adrzdv.mtocp.domain.repository.DepotRepository;
import com.adrzdv.mtocp.domain.repository.TrainRepository;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;
import com.adrzdv.mtocp.util.importmanager.handlers.CompanyImportHandler;
import com.adrzdv.mtocp.util.importmanager.handlers.DepotImportHandler;
import com.adrzdv.mtocp.util.importmanager.ImportHandlerRegistry;
import com.adrzdv.mtocp.util.importmanager.ImportManager;
import com.adrzdv.mtocp.util.importmanager.handlers.TrainImportHandler;
import com.adrzdv.mtocp.util.importmanager.handlers.ViolationImportHandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App extends Application {
    private static App instance;
    private static Toast currentToast;

    private AppDatabase database;
    private static ViolationRepository violationRepository;
    private static DepotRepository depotRepository;
    private static CompanyRepository companyRepository;
    private static TrainRepository trainRepository;
    private static ExecutorService executor;
    private static ImportHandlerRegistry registry;
    private static ImportManager importManager;

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

        initRepositories();
        executor = Executors.newSingleThreadExecutor();
        regHandlers();
        importManager = new ImportManager(registry, executor);

    }

    public AppDatabase getDatabase() {
        return database;
    }


    public static ImportManager getImportManager() {
        return importManager;
    }

    public static ViolationRepository getViolationRepository() {
        return violationRepository;
    }

    public static DepotRepository getDepotRepository() {
        return depotRepository;
    }

    public static CompanyRepository getCompanyRepository() {
        return companyRepository;
    }

    public static TrainRepository getTrainRepository() {
        return trainRepository;
    }

    public static void showToast(Context context, String message) {
        if (currentToast != null) {
            currentToast.cancel();
        }
        currentToast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        currentToast.show();
    }

    private void initRepositories() {
        violationRepository = new ViolationRepositoryImpl(database.violationDao());
        depotRepository = new DepotRepositoryImpl(database.depotDao());
        companyRepository = new CompanyRepositoryImpl(database.companyDao());
        trainRepository = new TrainRepositoryImpl(database.trainDao(), database.depotDao());
    }

    private void regHandlers() {
        registry = new ImportHandlerRegistry();
        registry.register(ViolationImport.class,
                new ViolationImportHandler(violationRepository,
                        msg -> Log.d("IMPORT", msg)));
        registry.register(DepotImport.class,
                new DepotImportHandler(depotRepository,
                        msg -> Log.d("IMPORT", msg)));
        registry.register(CompanyImport.class,
                new CompanyImportHandler(companyRepository,
                        msg -> Log.d("IMPORT", msg)));
        registry.register(TrainImport.class,
                new TrainImportHandler(trainRepository,
                        msg -> Log.d("IMPORT", msg)));
    }
}
