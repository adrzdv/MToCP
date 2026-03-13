package com.adrzdv.mtocp.mapper

import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain
import com.adrzdv.mtocp.ui.model.statedtoui.ViolationUi

fun ViolationEntity.toUi() = ViolationUi(
    this.code,
    this.name,
    this.shortName,
    1,
    false
)

fun ViolationUi.toDomain() = ViolationDomain(
    code,
    description,
    shortDescription
)

fun ViolationDomain.toUi() = ViolationUi(
    code,
    name,
    shortName,
    amount,
    isResolved,
    attributeMap
)