package com.adrzdv.mtocp.util.importmanager;

import static com.adrzdv.mtocp.MessageCodes.SUCCESS;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.data.importmodel.ViolationImport;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;
import com.adrzdv.mtocp.mapper.ViolationMapper;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

public class ViolationImportHandler implements ImportHandler<ViolationImport> {

    private final ViolationRepository repository;
    private final Consumer<String> toastCallback;

    public ViolationImportHandler(
            ViolationRepository repository,
            Consumer<String> toastCallback
    ) {
        this.repository = repository;
        this.toastCallback = toastCallback;
    }

    @Override
    public void handle(List<ViolationImport> items) {

        List<ViolationEntity> entities = items.stream()
                .map(ViolationMapper::fromImportToEntity)
                .toList();
        repository.saveAll(entities);
        toastCallback.accept(SUCCESS.toString());

    }
}
