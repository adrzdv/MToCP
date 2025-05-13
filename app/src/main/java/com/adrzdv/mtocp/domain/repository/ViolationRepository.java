package com.adrzdv.mtocp.domain.repository;

import com.adrzdv.mtocp.data.db.entity.ViolationEntity;

import java.util.List;

public interface ViolationRepository {

    List<ViolationEntity> getAll();
}
