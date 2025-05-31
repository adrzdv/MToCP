package com.adrzdv.mtocp.ui.viewmodel;

import static com.adrzdv.mtocp.MessageCodes.SUCCESS;
import static com.adrzdv.mtocp.MessageCodes.UPDATE_ERROR;
import static com.adrzdv.mtocp.domain.model.enums.RevisionType.ALL;

import android.database.sqlite.SQLiteConstraintException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.data.importmodel.ViolationImport;
import com.adrzdv.mtocp.domain.model.enums.RevisionType;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;
import com.adrzdv.mtocp.mapper.ViolationMapper;
import com.adrzdv.mtocp.ui.model.ViolationDto;
import com.adrzdv.mtocp.util.Event;
import com.adrzdv.mtocp.util.importmanager.ImportManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ViolationViewModel extends ViewModel {

    private final ViolationRepository repository;
    private final MutableLiveData<List<ViolationDto>> filteredViolations;
    private final MutableLiveData<Event<String>> toastMessage;
    //private final ImportHandlerRegistry registry;
    //private final ImportManager manager;
    private List<ViolationEntity> allViolations;
    private String currentSearchString;
    private RevisionType currentRevisionType;
    private final ExecutorService executor;


    public ViolationViewModel(ViolationRepository repository) {
        this.repository = repository;
        this.filteredViolations = new MutableLiveData<>(new ArrayList<>());
        this.toastMessage = new MutableLiveData<>();
        this.allViolations = new ArrayList<>();
        //registry = new ImportHandlerRegistry();
        //this.manager = manager;
        //registry.register(ViolationImport.class, this::handle);
        this.currentSearchString = "";
        this.currentRevisionType = ALL;
        this.executor = Executors.newSingleThreadExecutor();
        //manager = new ImportManager(registry, executor);
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

        executor.execute(() -> {
            try {
                repository.updateByCode(violation);
                loadViolations();
                toastMessage.postValue(new Event<>("UPDATED"));
            } catch (SQLiteConstraintException e) {
                toastMessage.postValue(new Event<>(UPDATE_ERROR.toString()));
            }
        });
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

    private void handle(List<ViolationImport> list) {
        executor.execute(() -> {
            List<ViolationEntity> entities = list.stream()
                    .map(ViolationMapper::fromImportToEntity)
                    .toList();
            repository.saveAll(entities);
            toastMessage.postValue(new Event<>(SUCCESS.getErrorTitle()));
        });
    }
}
