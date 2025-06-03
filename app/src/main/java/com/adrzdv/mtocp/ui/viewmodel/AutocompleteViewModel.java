package com.adrzdv.mtocp.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adrzdv.mtocp.data.db.entity.TrainEntity;
import com.adrzdv.mtocp.domain.model.enums.OrdersTypes;
import com.adrzdv.mtocp.domain.repository.TrainRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AutocompleteViewModel extends ViewModel {

    private final MutableLiveData<List<String>> filteredItems = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> query = new MutableLiveData<>("");
    private List<String> allItems = new ArrayList<>();
    private final TrainRepository trainRepository;

    public AutocompleteViewModel(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public LiveData<List<String>> getFilteredItems() {
        return filteredItems;
    }

    public LiveData<String> getQuery() {
        return query;
    }

    public void setOrderType(OrdersTypes type) {

        Executor executor = Executors.newSingleThreadExecutor();

        switch (type) {
            case PASSENGER_TRAIN:
                executor.execute(() -> allItems = trainRepository.getAll().stream()
                        .map(TrainEntity::toString)
                        .toList());
                break;

            case TICKET_OFFICE:
                allItems = List.of("Касса A", "Касса B", "Касса C");
                break;
            default:
                allItems = new ArrayList<>();
        }
        filterItems(query.getValue());
    }

    public void onQueryChanged(String input) {
        query.setValue(input);
        filterItems(input);
    }

    private void filterItems(String input) {
        if (input == null || input.isBlank()) {
            filteredItems.setValue(Collections.emptyList());
            return;
        }

        List<String> result = allItems.stream()
                .filter(item -> item.toLowerCase().contains(input.toLowerCase()))
                .toList();

        filteredItems.setValue(result);
    }
}
