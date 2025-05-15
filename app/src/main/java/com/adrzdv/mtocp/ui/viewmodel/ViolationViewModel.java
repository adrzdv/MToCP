package com.adrzdv.mtocp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;

import java.util.List;

public class ViolationViewModel extends ViewModel {

    private final MutableLiveData<List<ViolationEntity>> violations = new MutableLiveData<>();
    private final ViolationRepository repository;

    public ViolationViewModel(ViolationRepository repository) {
        this.repository = repository;
        loadViolations();
    }

    private void loadViolations() {

        new Thread(() -> {
            List<ViolationEntity> violationList = repository.getAll();
            violations.postValue(violationList);
        }).start();
    }

    public LiveData<List<ViolationEntity>> getViolations() {
        return violations;
    }
}
