package com.adrzdv.mtocp.domain.usecase

import com.adrzdv.mtocp.domain.model.departments.CompanyDomain
import com.adrzdv.mtocp.domain.repository.CompanyRepository
import com.adrzdv.mtocp.mapper.CompanyMapper

class GetCompanyByNameUseCase(
    private val companyRepository: CompanyRepository
) {
    suspend operator fun invoke(name: String): CompanyDomain {
        return companyRepository.getAll().map { CompanyMapper.fromPojoToDomain(it) }
            .firstOrNull {
                it.name.equals(name, ignoreCase = true)
            }
            ?: throw IllegalArgumentException("Company $name not found")
    }
}