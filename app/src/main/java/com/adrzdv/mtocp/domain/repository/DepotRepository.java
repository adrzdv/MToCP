package com.adrzdv.mtocp.domain.repository;

import com.adrzdv.mtocp.data.db.pojo.DepotWithBranch;

import java.util.List;

public interface DepotRepository {

    List<DepotWithBranch> getAll();

    DepotWithBranch getDepotById(int id);

    void saveAll(List<DepotWithBranch> list);

    List<DepotWithBranch> getDinnerDepots();
}
