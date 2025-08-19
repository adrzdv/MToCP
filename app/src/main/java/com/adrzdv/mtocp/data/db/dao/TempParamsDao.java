package com.adrzdv.mtocp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.adrzdv.mtocp.data.db.entity.TempParametersEntity;

import java.util.List;

@Dao
public interface TempParamsDao {

    @Query("SELECT * FROM temp_parameters WHERE date(date_end) > date('now') AND is_active = 1")
    List<TempParametersEntity> getAll();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TempParametersEntity> entities);
}
