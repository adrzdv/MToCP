package com.adrzdv.mtocp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;

import java.util.List;

@Dao
public interface ViolationDao {

    @Query("SELECT * FROM violations WHERE is_active = 1")
    List<ViolationEntity> getAll();

    @Transaction
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void addNew(ViolationEntity violation);

    @Transaction
    @Query("""
                UPDATE violations SET
                    name = :name,
                    short_name = :shortName,
                    is_active = :isActive,
                    in_transit = :inTransit,
                    at_start_point = :atStartPoint,
                    at_turnround_point = :atTurnroundPoint,
                    at_ticket_office = :atTicketOffice
                WHERE code = :code
            """)
    void updateByCode(
            int code,
            String name,
            String shortName,
            boolean isActive,
            boolean inTransit,
            boolean atStartPoint,
            boolean atTurnroundPoint,
            boolean atTicketOffice
    );

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ViolationEntity> violations);
}
