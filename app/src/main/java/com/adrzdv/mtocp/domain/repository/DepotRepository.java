package com.adrzdv.mtocp.domain.repository;

import com.adrzdv.mtocp.data.db.entity.BranchEntity;
import com.adrzdv.mtocp.data.db.entity.DepotEntity;
import com.adrzdv.mtocp.data.db.entity.DepotWithBranch;

import java.util.List;

public interface DepotRepository {

    List<DepotWithBranch> getAll();

    DepotWithBranch getDepotById(int id);

    void saveAll(List<DepotWithBranch> list);
}
