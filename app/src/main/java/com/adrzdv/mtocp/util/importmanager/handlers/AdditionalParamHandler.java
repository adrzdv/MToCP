package com.adrzdv.mtocp.util.importmanager.handlers;

import com.adrzdv.mtocp.MessageCodes;
import com.adrzdv.mtocp.data.db.entity.TempParametersEntity;
import com.adrzdv.mtocp.data.importmodel.AdditionalParamImport;
import com.adrzdv.mtocp.domain.repository.TempParamRepository;
import com.adrzdv.mtocp.mapper.StaticsParametersMapper;

import java.util.List;
import java.util.function.Consumer;

public class AdditionalParamHandler extends BaseImportHandler<AdditionalParamImport> {

    private final TempParamRepository repo;

    public AdditionalParamHandler(TempParamRepository repo, Consumer<String> message) {
        super(message);
        this.repo = repo;
    }

    @Override
    public void handle(List<AdditionalParamImport> items) {
        List<TempParametersEntity> entities = items.stream()
                .map(StaticsParametersMapper::fromImportToEntity)
                .toList();
        repo.saveAll(entities);
        showSuccessToast(MessageCodes.SUCCESS.toString());
    }
}
