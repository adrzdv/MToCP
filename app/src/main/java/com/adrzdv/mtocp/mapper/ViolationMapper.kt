package com.adrzdv.mtocp.mapper

import com.adrzdv.mtocp.data.db.entity.ViolationEntity
import com.adrzdv.mtocp.domain.model.violation.StaticsParam
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain
import com.adrzdv.mtocp.ui.model.dto.StaticsParamUi
import com.adrzdv.mtocp.ui.model.dto.ViolationUi

fun ViolationEntity.toUi() = ViolationUi(
    this.code,
    this.name,
    this.shortName,
    1,
    false
)

fun ViolationUi.toDomain(): ViolationDomain {
    val violation = ViolationDomain(
        code,
        description,
        shortDescription
    )
    violation.amount = value
    violation.isResolved = isResolved
    violation.attributeList = attributes
    violation.mediaPaths = mediaPaths

    return violation
}

fun ViolationDomain.toUi() = ViolationUi(
    code,
    name,
    shortName,
    amount,
    isResolved,
    attributeList
)

fun StaticsParamUi.toDomain() = StaticsParam(
    id,
    name,
    isCompleted,
    note
)