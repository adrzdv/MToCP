package com.adrzdv.mtocp.data.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.adrzdv.mtocp.data.db.converter.DataConverter;
import com.adrzdv.mtocp.data.db.dao.DepotDao;
import com.adrzdv.mtocp.data.db.dao.ViolationDao;
import com.adrzdv.mtocp.data.db.entity.BranchEntity;
import com.adrzdv.mtocp.data.db.entity.DepotEntity;
import com.adrzdv.mtocp.data.db.entity.TempParametersEntity;
import com.adrzdv.mtocp.data.db.entity.ViolationEntity;

@Database(entities = {ViolationEntity.class,
        TempParametersEntity.class,
        DepotEntity.class,
        BranchEntity.class},
        version = 1,
        exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract ViolationDao violationDao();

    public abstract DepotDao depotDao();
}
