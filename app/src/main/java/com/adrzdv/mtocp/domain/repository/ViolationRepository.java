package com.adrzdv.mtocp.domain.repository;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Query;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;

import java.util.List;

public interface ViolationRepository {

    List<ViolationEntity> getAll();

    void addNew(ViolationEntity violation);

    void updateByCode(ViolationEntity violation) throws SQLiteConstraintException;

    void saveAll(List<ViolationEntity> list);

}
