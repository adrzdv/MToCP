package com.adrzdv.mtocp.data.repository;

import com.adrzdv.mtocp.data.db.dao.DepotDao;
import com.adrzdv.mtocp.data.db.entity.DepotWithBranch;
import com.adrzdv.mtocp.domain.repository.DepotRepository;

import java.util.List;

public class DepotRepositoryImpl implements DepotRepository {

    private final DepotDao dao;

    public DepotRepositoryImpl(DepotDao dao) {
        this.dao = dao;
    }

    @Override
    public List<DepotWithBranch> getAll() {
        return dao.getDepots();
    }

    @Override
    public DepotWithBranch getDepotById(int id) {
        return dao.getDepotById(id);
    }

    @Override
    public void saveAll(List<DepotWithBranch> list) {

        for (DepotWithBranch depot : list) {
            dao.insertDepotWithBranch(depot.depot, depot.branch);
        }
    }
}
