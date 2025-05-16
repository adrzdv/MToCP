package com.adrzdv.mtocp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;

import java.util.List;

@Dao
public interface ViolationDao {

    @Query("SELECT * FROM violations WHERE is_active = 1")
    List<ViolationEntity> getAll();
}
