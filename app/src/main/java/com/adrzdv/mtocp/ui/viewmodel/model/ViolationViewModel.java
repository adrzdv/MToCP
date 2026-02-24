package com.adrzdv.mtocp.ui.viewmodel.model;

import static com.adrzdv.mtocp.domain.model.enums.RevisionType.ALL;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.domain.model.enums.RevisionType;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;
import com.adrzdv.mtocp.mapper.ViolationMapper;
import com.adrzdv.mtocp.ui.model.ViolationDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViolationViewModel extends ViewModel {

    private final ViolationRepository repository;
    private final MutableLiveData<List<ViolationDto>> filteredViolations;
    private List<ViolationEntity> allViolations;
    private String currentSearchString;
    private RevisionType currentRevisionType;
    private final ExecutorService executor;


    public ViolationViewModel(ViolationRepository repository) {
        this.repository = repository;
        this.filteredViolations = new MutableLiveData<>(new ArrayList<>());
        this.allViolations = new ArrayList<>();
        this.currentSearchString = "";
        this.currentRevisionType = ALL;
        this.executor = Executors.newSingleThreadExecutor();
        loadViolations();
    }

    private void loadViolations() {

        executor.execute(() -> {
            allViolations = repository.getAll();
            applyFilters();
        });
    }

    public LiveData<List<ViolationDto>> getFilteredViolations() {
        return filteredViolations;
    }

    public void filterDataByString(String str) {
        currentSearchString = str != null ? str.trim() : "";
        applyFilters();
    }

    public void filterDataByRevisionType(RevisionType revType) {
        currentRevisionType = revType != null ? revType : ALL;
        applyFilters();
    }

    private void applyFilters() {
        List<ViolationEntity> result = new ArrayList<>(allViolations);

        result = switch (currentRevisionType) {
            case IN_TRANSIT -> result.stream().filter(ViolationEntity::getInTransit).toList();
            case AT_START_POINT ->
                    result.stream().filter(ViolationEntity::getAtStartPoint).toList();
            case AT_TURNROUND_POINT ->
                    result.stream().filter(ViolationEntity::getAtTurnroundPoint).toList();
            case AT_TICKET_OFFICE ->
                    result.stream().filter(ViolationEntity::getAtTicketOffice).toList();
            default -> result;
        };

        String str = currentSearchString.toLowerCase();
        if (!str.isEmpty()) {
            result = result.stream().filter(violation -> {
                String name = violation.getName().toLowerCase();
                String code = String.valueOf(violation.getCode());
                return name.contains(str) || code.contains(str);
            }).toList();
        }

        List<ViolationDto> dtoList = result.stream()
                .map(ViolationMapper::fromEntityToDto)
                .toList();

        filteredViolations.postValue(dtoList);
    }

}
