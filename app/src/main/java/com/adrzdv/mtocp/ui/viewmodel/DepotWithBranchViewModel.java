package com.adrzdv.mtocp.ui.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.data.db.entity.BranchEntity;
import com.adrzdv.mtocp.data.db.entity.DepotEntity;
import com.adrzdv.mtocp.data.db.entity.DepotWithBranch;
import com.adrzdv.mtocp.data.importmodel.DepotImport;
import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.repository.DepotRepository;
import com.adrzdv.mtocp.mapper.BranchMapper;
import com.adrzdv.mtocp.mapper.DepotMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DepotWithBranchViewModel extends ViewModel {
    private final DepotRepository repository;
    private final MutableLiveData<List<DepotDomain>> depotList;
    private final ExecutorService executor;

    public DepotWithBranchViewModel(DepotRepository repository) {
        this.repository = repository;
        this.executor = Executors.newSingleThreadExecutor();
        depotList = new MutableLiveData<>(new ArrayList<>());
        loadData();
    }

    private void loadData() {
        executor.execute(() -> {
            List<DepotWithBranch> depotListFromDb = repository.getAll();
            depotList.postValue(depotListFromDb.stream()
                    .map(DepotMapper::fromPOJOEntityToDomain)
                    .toList());
        });
    }

    public MutableLiveData<List<DepotDomain>> getDepotList() {
        return depotList;
    }

}
