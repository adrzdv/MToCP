package com.adrzdv.mtocp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.data.db.entity.DepotWithBranch;
import com.adrzdv.mtocp.domain.model.departments.DepotDomain;
import com.adrzdv.mtocp.domain.repository.DepotRepository;
import com.adrzdv.mtocp.mapper.DepotMapper;
import com.adrzdv.mtocp.ui.model.DepotDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DepotViewModel extends ViewModel {
    private final DepotRepository repository;
    private List<DepotDomain> allDepotList;
    private final MutableLiveData<List<DepotDto>> filteredDepotList;
    private String currentSearchString;
    private final ExecutorService executor;

    public DepotViewModel(DepotRepository repository) {
        this.repository = repository;
        this.executor = Executors.newSingleThreadExecutor();
        this.allDepotList = new ArrayList<>();
        this.filteredDepotList = new MutableLiveData<>(new ArrayList<>());
        this.currentSearchString = "";
        loadData();
    }

    public LiveData<List<DepotDto>> getFilteredDepots() {
        return filteredDepotList;
    }

    public void filterByString(String string) {
        currentSearchString = string != null ? string.trim() : "";
        applyFilter();
    }

    private void applyFilter() {
        String str = currentSearchString.toLowerCase();
        List<DepotDomain> res = new ArrayList<>(allDepotList);

        if (!str.isEmpty()) {
            res = res.stream()
                    .filter(depot -> depot.getName().toLowerCase().contains(str))
                    .toList();
        }

        List<DepotDto> dtoList = res.stream()
                .map(DepotMapper::fromDomainToDto)
                .toList();

        filteredDepotList.postValue(dtoList);
    }

    private void loadData() {
        executor.execute(() -> {
            List<DepotWithBranch> depotListFromDb = repository.getAll();
            allDepotList = depotListFromDb.stream().map(DepotMapper::fromJoinModelToDomain)
                    .toList();
            applyFilter();
        });
    }

}
