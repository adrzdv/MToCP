package com.adrzdv.mtocp.di;

import android.app.Application;

import androidx.room.Room;

import com.adrzdv.mtocp.data.db.AppDatabase;
import com.adrzdv.mtocp.data.db.dao.ViolationDao;
import com.adrzdv.mtocp.data.repository.ViolationRepositoryImpl;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(Application application) {
        return Room.databaseBuilder(application.getApplicationContext(), AppDatabase.class, "mtocpdb")
                .createFromAsset("database/mtocpdb.db")
                .build();
    }

    @Provides
    @Singleton
    public ViolationRepository provideViolationRepository(ViolationDao dao) {
        return new ViolationRepositoryImpl(dao);
    }

}
