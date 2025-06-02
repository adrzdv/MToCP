package com.adrzdv.mtocp.util.importmanager.handlers;

import static com.adrzdv.mtocp.MessageCodes.SUCCESS;

import com.adrzdv.mtocp.data.db.entity.CompanyEntity;
import com.adrzdv.mtocp.data.importmodel.CompanyImport;
import com.adrzdv.mtocp.domain.repository.CompanyRepository;
import com.adrzdv.mtocp.mapper.CompanyMapper;

import java.util.List;
import java.util.function.Consumer;

public class CompanyImportHandler extends BaseImportHandler<CompanyImport> {

    private final CompanyRepository repository;

    public CompanyImportHandler(
            CompanyRepository repository,
            Consumer<String> toastCollback
    ) {
        super(toastCollback);
        this.repository = repository;
    }

    @Override
    public void handle(List<CompanyImport> items) {
        List<CompanyEntity> entities = items.stream()
                .map(CompanyMapper::fromImportToEntity)
                .toList();
        repository.saveAll(entities);
        showSuccessToast(SUCCESS.toString());
    }
}
