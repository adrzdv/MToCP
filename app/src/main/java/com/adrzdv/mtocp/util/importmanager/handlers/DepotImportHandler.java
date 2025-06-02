package com.adrzdv.mtocp.util.importmanager.handlers;

import static com.adrzdv.mtocp.MessageCodes.SUCCESS;

import com.adrzdv.mtocp.data.db.entity.BranchEntity;
import com.adrzdv.mtocp.data.db.entity.DepotEntity;
import com.adrzdv.mtocp.data.db.entity.DepotWithBranch;
import com.adrzdv.mtocp.data.importmodel.DepotImport;
import com.adrzdv.mtocp.domain.repository.DepotRepository;
import com.adrzdv.mtocp.mapper.BranchMapper;
import com.adrzdv.mtocp.mapper.DepotMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class DepotImportHandler extends BaseImportHandler<DepotImport> {

    private final DepotRepository repository;

    public DepotImportHandler(DepotRepository depotRepository,
                              Consumer<String> toastCallback) {
        super(toastCallback);
        this.repository = depotRepository;
    }

    @Override
    public void handle(List<DepotImport> items) {

        List<DepotEntity> depotEntities = items.stream()
                .map(DepotMapper::fromImportToEntity)
                .toList();

        Map<Integer, BranchEntity> branchMap = new HashMap<>();

        for (DepotImport depotImport : items) {
            BranchEntity branchEntity = BranchMapper.fromImportToEntity(depotImport.getBranch());
            branchMap.put(branchEntity.getId(), branchEntity);
        }

        List<DepotWithBranch> depWithBrList = new ArrayList<>();

        for (DepotEntity depot : depotEntities) {
            DepotWithBranch depotWithBranch = new DepotWithBranch();
            depotWithBranch.depot = depot;
            if (branchMap.containsKey(depot.getBranchId())) {
                depotWithBranch.branch = branchMap.get(depot.getBranchId());
            }
            depWithBrList.add(depotWithBranch);
        }

        repository.saveAll(depWithBrList);
        showSuccessToast(SUCCESS.toString());
    }
}
