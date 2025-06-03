package com.adrzdv.mtocp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.adrzdv.mtocp.data.db.entity.CompanyEntity;
import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch;

import java.util.List;

@Dao
public interface CompanyDao {

    @Transaction
    @Query("SELECT * FROM companies WHERE is_active = 1 AND date(expiration_date) > date('now') ")
    List<CompanyWithBranch> getAll();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CompanyEntity> entities);
}
