package com.adrzdv.mtocp.di;

import android.app.Application;

import androidx.room.Room;

import com.adrzdv.mtocp.data.db.AppDatabase;
import com.adrzdv.mtocp.data.db.dao.ViolationDao;
import com.adrzdv.mtocp.data.repository.ViolationRepositoryImpl;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;

@Deprecated
public class DatabaseModule {

    public AppDatabase provideAppDatabase(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(), AppDatabase.class, "mtocpdb")
                .createFromAsset("database/mtocpdb.db")
                .build();
    }

    public ViolationRepository provideViolationRepository(ViolationDao dao) {
        return new ViolationRepositoryImpl(dao);
    }

    public ViolationDao provideViolationDao(AppDatabase appDatabase) {
        return appDatabase.violationDao();  // Room создаст реализацию
    }

}
