package com.adrzdv.mtocp.domain.repository.old;

import com.adrzdv.mtocp.data.db.entity.TempParametersEntity;
import com.adrzdv.mtocp.domain.repository.BaseAppRepository;

import java.util.List;

public interface TempParamRepository {
    List<TempParametersEntity> getAll();

    void saveAll(List<TempParametersEntity> entities);
}
