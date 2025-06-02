package com.adrzdv.mtocp.util.importmanager.handlers;

import static com.adrzdv.mtocp.MessageCodes.SUCCESS;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;
import com.adrzdv.mtocp.data.importmodel.ViolationImport;
import com.adrzdv.mtocp.domain.repository.ViolationRepository;
import com.adrzdv.mtocp.mapper.ViolationMapper;

import java.util.List;
import java.util.function.Consumer;

public class ViolationImportHandler extends BaseImportHandler<ViolationImport> {

    private final ViolationRepository repository;

    public ViolationImportHandler(
            ViolationRepository repository,
            Consumer<String> toastCallback
    ) {
        super(toastCallback);
        this.repository = repository;
    }

    @Override
    public void handle(List<ViolationImport> items) {

        List<ViolationEntity> entities = items.stream()
                .map(ViolationMapper::fromImportToEntity)
                .toList();
        repository.saveAll(entities);
        showSuccessToast(SUCCESS.toString());
    }
}
