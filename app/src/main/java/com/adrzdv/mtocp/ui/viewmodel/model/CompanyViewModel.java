package com.adrzdv.mtocp.ui.viewmodel.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.data.db.entity.CompanyWithBranch;
import com.adrzdv.mtocp.domain.model.departments.CompanyDomain;
import com.adrzdv.mtocp.domain.repository.CompanyRepository;
import com.adrzdv.mtocp.mapper.CompanyMapper;
import com.adrzdv.mtocp.ui.model.CompanyDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompanyViewModel extends ViewModel {
    private final CompanyRepository repository;
    private List<CompanyDomain> allCompanies;
    private final MutableLiveData<List<CompanyDto>> filteredList;
    private String currSearchStr;
    private final ExecutorService executor;
    private boolean isDinner;

    public CompanyViewModel(CompanyRepository repository) {
        this.repository = repository;
        this.executor = Executors.newSingleThreadExecutor();
        this.allCompanies = new ArrayList<>();
        this.filteredList = new MutableLiveData<>(new ArrayList<>());
        this.currSearchStr = "";
        this.isDinner = false;
        loadData();
    }

    public CompanyDomain getCompanyDomain(String companyName) {

        return allCompanies.stream()
                .filter(company -> company.getName().equals(companyName))
                .findFirst().orElse(null);
    }

    public LiveData<List<CompanyDto>> getFilteredCompanies() {
        return filteredList;
    }

    public void filterByString(String str) {
        currSearchStr = str != null ? str.trim() : "";
        applyFilter();
    }

    public void filterDinner() {
        isDinner = true;
        applyFilter();
    }

    public void resetDinnerFilter() {
        isDinner = false;
        applyFilter();
    }

    private void applyFilter() {
        String str = currSearchStr.toLowerCase();
        List<CompanyDomain> res = new ArrayList<>(allCompanies);

        if (isDinner) {
            res = res.stream()
                    .filter(company ->
                            Boolean.TRUE.equals(company.getDinnerDepartment()))
                    .toList();
        } else {
            res = res.stream()
                    .filter(company ->
                            Boolean.TRUE.equals(company.getDinnerDepartment() == null))
                    .toList();
        }

        if (!str.isEmpty()) {
            res = res.stream()
                    .filter(company -> company.getName().toLowerCase().contains(str))
                    .toList();
        }

        List<CompanyDto> dtoList = res.stream()
                .map(CompanyMapper::fromDomainToDto)
                .toList();

        filteredList.postValue(dtoList);
    }

    private void loadData() {
        executor.execute(() -> {
            List<CompanyWithBranch> companiesFromDb = repository.getAll();
            allCompanies = companiesFromDb.stream()
                    .map(CompanyMapper::fromPojoToDomain)
                    .toList();
            applyFilter();
        });
    }
}
