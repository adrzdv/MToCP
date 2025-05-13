package com.adrzdv.mtocp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ViolationViewModel extends ViewModel {
    private final MutableLiveData<List<ViolationEntity>> violations = new MutableLiveData<>();

    @Inject
    public ViolationViewModel(ViolationRepository repository) {
        List<ViolationEntity> violationList = repository.getAll();
        violations.setValue(violationList);
    }

    public LiveData<List<ViolationEntity>> getViolations() {
        return violations;
    }
}
