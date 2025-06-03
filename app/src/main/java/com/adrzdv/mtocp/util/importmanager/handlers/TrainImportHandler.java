package com.adrzdv.mtocp.util.importmanager.handlers;

import static com.adrzdv.mtocp.MessageCodes.SUCCESS;

import com.adrzdv.mtocp.data.db.entity.TrainEntity;
import com.adrzdv.mtocp.data.importmodel.TrainImport;
import com.adrzdv.mtocp.domain.repository.TrainRepository;
import com.adrzdv.mtocp.mapper.TrainMapper;

import java.util.List;
import java.util.function.Consumer;

public class TrainImportHandler extends BaseImportHandler<TrainImport> {

    private final TrainRepository repository;

    public TrainImportHandler(TrainRepository repository,
                              Consumer<String> toastCallback) {
        super(toastCallback);
        this.repository = repository;
    }

    @Override
    public void handle(List<TrainImport> items) {
        List<TrainEntity> entities = items.stream()
                .map(TrainMapper::fromImportToEntity)
                .toList();
        repository.saveAll(entities);
        showSuccessToast(SUCCESS.toString());
    }
}
