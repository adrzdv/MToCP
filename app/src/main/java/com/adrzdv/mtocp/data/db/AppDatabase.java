package com.adrzdv.mtocp.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.adrzdv.mtocp.data.db.converter.DataConverter;
import com.adrzdv.mtocp.data.db.entity.TempParametersEntity;
import com.adrzdv.mtocp.data.db.entity.ViolationEntity;

@Database(entities = {ViolationEntity.class, TempParametersEntity.class},
        version = 1,
        exportSchema = false)
@TypeConverters({DataConverter.class})
public class AppDatabase extends RoomDatabase {

    @NonNull
    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {

    }
}
