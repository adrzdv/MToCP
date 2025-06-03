package com.adrzdv.mtocp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.adrzdv.mtocp.data.db.entity.TrainEntity;

import java.util.List;

@Dao
public interface TrainDao {

    @Transaction
    @Query("SELECT * FROM trains")
    List<TrainEntity> getAll();

    @Transaction
    @Query("SELECT * FROM trains WHERE number = :number")
    TrainEntity getTrainByString(String number);

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TrainEntity> trains);
}
