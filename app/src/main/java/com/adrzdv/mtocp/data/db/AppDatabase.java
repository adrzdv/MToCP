package com.adrzdv.mtocp.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.adrzdv.mtocp.data.db.converter.DataConverter;
import com.adrzdv.mtocp.data.db.dao.CompanyDao;
import com.adrzdv.mtocp.data.db.dao.DepotDao;
import com.adrzdv.mtocp.data.db.dao.TrainDao;
import com.adrzdv.mtocp.data.db.dao.ViolationDao;
import com.adrzdv.mtocp.data.db.entity.BranchEntity;
import com.adrzdv.mtocp.data.db.entity.CompanyEntity;
import com.adrzdv.mtocp.data.db.entity.DepotEntity;
import com.adrzdv.mtocp.data.db.entity.TempParametersEntity;
import com.adrzdv.mtocp.data.db.entity.TrainEntity;
import com.adrzdv.mtocp.data.db.entity.ViolationEntity;

@Database(entities = {ViolationEntity.class,
        TempParametersEntity.class,
        CompanyEntity.class,
        DepotEntity.class,
        BranchEntity.class,
        TrainEntity.class},
        version = 1,
        exportSchema = false)
@TypeConverters({DataConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract ViolationDao violationDao();

    public abstract DepotDao depotDao();

    public abstract CompanyDao companyDao();

    public abstract TrainDao trainDao();
}
