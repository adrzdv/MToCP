package com.adrzdv.mtocp.ui.viewmodel;

import static com.adrzdv.mtocp.ErrorCodes.UPDATE_ERROR;
import static com.adrzdv.mtocp.domain.model.enums.RevisionType.ALL;

import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.App;
import com.adrzdv.mtocp.ErrorCodes;
import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.data.importmodel.ViolationImport;
import com.adrzdv.mtocp.domain.model.enums.RevisionType;
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;
import com.adrzdv.mtocp.mapper.ViolationMapper;
import com.adrzdv.mtocp.ui.model.ViolationDto;
import com.adrzdv.mtocp.util.Event;
import com.adrzdv.mtocp.util.dataiomanager.DataIOManager;
import com.adrzdv.mtocp.util.dataiomanager.DataIOManagerFactory;
import com.adrzdv.mtocp.util.dataiomanager.JsonIOManagerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class ViolationViewModel extends ViewModel {

    private final ViolationRepository repository;
    private final MutableLiveData<List<ViolationDto>> filteredViolations = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Event<String>> toastMessage = new MutableLiveData<>();
    private List<ViolationEntity> allViolations = new ArrayList<>();
    private String currentSearchString = "";
    private RevisionType currentRevisionType = ALL;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ViolationViewModel(ViolationRepository repository) {
        this.repository = repository;
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

    public LiveData<Event<String>> getToastMessage() {
        return toastMessage;
    }

    public void updateViolation(ViolationEntity violation) {
        new Thread(() -> {
            try {
                repository.updateByCode(violation);
                loadViolations();
            } catch (SQLiteConstraintException e) {
                toastMessage.postValue(new Event<>(ErrorCodes.UPDATE_ERROR.toString()));
            }
        }).start();
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

    public void importViolationFromJson(Context context, Uri uri) {
        JsonIOManagerFactory<ViolationImport> factory = new JsonIOManagerFactory<>();
        DataIOManager<ViolationImport> manager = factory.createManager(ViolationImport.class);
        List<ViolationImport> importedList = manager.importData(context, uri);

        if (importedList != null && !importedList.isEmpty()) {

            executor.execute(() -> {
                repository.saveAll(importedList.stream()
                        .map(ViolationMapper::fromImportToEntity)
                        .collect(Collectors.toList()));
            });

        }

    }
}
