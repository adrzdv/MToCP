package com.adrzdv.mtocp.data.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.adrzdv.mtocp.data.db.entity.BranchEntity;
import com.adrzdv.mtocp.data.db.entity.DepotEntity;
import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch;

import java.util.List;

@Dao
public interface DepotDao {

    @Transaction
    @Query("SELECT * FROM depots WHERE is_active = 1")
    List<DepotWithBranch> getDepots();

    @Transaction
    @Query("SELECT * FROM depots WHERE id = :id AND is_active = 1")
    DepotWithBranch getDepotById(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertBranch(BranchEntity branch);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDepot(DepotEntity depot);

    @Transaction
    default void insertDepotWithBranch(DepotEntity depot, BranchEntity branch) {
        int branchId = (int) insertBranch(branch);
        depot.setBranchId(branchId);
        insertDepot(depot);
    }

    @Transaction
    @Query("SELECT * FROM depots WHERE is_active = 1 AND is_dinner_depot = 1")
    List<DepotWithBranch> getDinnerDepots();

}
