package com.adrzdv.mtocp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.adrzdv.mtocp.data.db.entity.TrainEntity;
import com.adrzdv.mtocp.data.db.pojo.TrainWithDepotAndBranch;

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

    @Transaction
    @Query("""
            SELECT
            t.id AS id,
            t.number AS number,
            t.route AS route,
            t.depot_id AS depot_id,
            t.is_video AS is_video,
            t.is_progressive AS is_progressive,
            d.id AS dpt_id,
            d.name AS dpt_name,
            d.s_name AS dpt_s_name,
            d.branch_id AS dpt_branch_id,
            d.is_active AS dpt_is_active,
            d.phone_number AS dpt_phone_number,
            d.is_dinner_depot AS dpt_is_dinner_depot,
            b.id AS br_id,
            b.name AS br_name,
            b.s_name AS br_s_name,
            b.is_alive AS br_is_alive,
            prp.id AS pde_id,
            prp.train_id AS pde_train_id,
            prp.date_start AS pde_date_start,
            prp.date_end AS pde_date_end
            FROM trains t
            LEFT JOIN progress_period prp ON t.id = prp.train_id
            LEFT JOIN depots d ON t.depot_id = d.id
            LEFT JOIN branches b ON d.branch_id = b.id
            """)
    List<TrainWithDepotAndBranch> getTrainWithFullData();

}
