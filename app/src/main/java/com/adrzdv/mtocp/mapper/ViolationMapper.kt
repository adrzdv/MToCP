package com.adrzdv.mtocp.mapper

import com.adrzdv.mtocp.data.db.entity.ViolationFullInfo
import com.adrzdv.mtocp.domain.model.violation.StaticsParam
import com.adrzdv.mtocp.domain.model.violation.ViolationDomain
import com.adrzdv.mtocp.ui.model.dto.StaticsParamUi
import com.adrzdv.mtocp.ui.model.dto.ViolationUi

fun ViolationFullInfo.toDomain() = ViolationDomain(
    violation.code,
    violation.description,
    violation.criteria.ifBlank { violation.measure }
)

fun ViolationFullInfo.toUi() = toDomain().toUi()
    .copy(
        criteria = violation.criteria,
        measure = violation.measure,
        revisionTypes = revisionTypes.map { it.name },
        departments = departments.map { it.shortName },
        divisions = divisions.map { division ->
            division.shortName.ifBlank { division.name }
        }
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
    attributes = attributeList
)

fun StaticsParamUi.toDomain() = StaticsParam(
    id,
    name,
    isCompleted,
    note
)
