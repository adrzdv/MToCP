package com.adrzdv.mtocp.domain.repository;

import com.adrzdv.mtocp.data.db.entity.TempParametersEntity;

import java.util.List;

public interface TempParamRepository {
    List<TempParametersEntity> getAll();

    void saveAll(List<TempParametersEntity> entities);
}
