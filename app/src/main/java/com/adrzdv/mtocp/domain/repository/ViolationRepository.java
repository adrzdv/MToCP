package com.adrzdv.mtocp.domain.repository;

import androidx.room.Query;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;

import java.util.List;

public interface ViolationRepository {

    @Query("SELECT * FROM violations WHERE is_active = 1")
    List<ViolationEntity> getAll();

}
