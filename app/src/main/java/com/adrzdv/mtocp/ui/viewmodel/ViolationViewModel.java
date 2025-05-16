package com.adrzdv.mtocp.ui.viewmodel;

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
import java.util.stream.Collectors;

public class ViolationViewModel extends ViewModel {

    private final ViolationRepository repository;
    private final MutableLiveData<List<ViolationDto>> filteredViolations = new MutableLiveData<>(new ArrayList<>());

    private List<ViolationEntity> allViolations = new ArrayList<>();
    private String currentSearchString = "";
    private RevisionType currentRevisionType = ALL;

    public ViolationViewModel(ViolationRepository repository) {
        this.repository = repository;
        loadViolations();
    }

    private void loadViolations() {
        new Thread(() -> {
            allViolations = repository.getAll();
            applyFilters();
        }).start();
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
            case IN_TRANSIT ->
                    result.stream().filter(ViolationEntity::getInTransit).collect(Collectors.toList());
            case AT_START_POINT ->
                    result.stream().filter(ViolationEntity::getAtStartPoint).collect(Collectors.toList());
            case AT_TURNROUND_POINT ->
                    result.stream().filter(ViolationEntity::getAtTurnroundPoint).collect(Collectors.toList());
            case AT_TICKET_OFFICE ->
                    result.stream().filter(ViolationEntity::getAtTicketOffice).collect(Collectors.toList());
            default -> result;
        };

        String str = currentSearchString.toLowerCase();
        if (!str.isEmpty()) {
            result = result.stream().filter(violation -> {
                String name = violation.getName().toLowerCase();
                String code = String.valueOf(violation.getCode());
                return name.contains(str) || code.contains(str);
            }).collect(Collectors.toList());
        }

        List<ViolationDto> dtoList = result.stream()
                .map(ViolationMapper::fromEntityToDto)
                .collect(Collectors.toList());

        filteredViolations.postValue(dtoList);
    }
}
