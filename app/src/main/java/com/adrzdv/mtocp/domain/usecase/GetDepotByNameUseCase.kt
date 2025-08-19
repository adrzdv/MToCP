package com.adrzdv.mtocp.domain.usecase

import com.adrzdv.mtocp.domain.model.departments.DepotDomain
import com.adrzdv.mtocp.domain.repository.DepotRepository
import com.adrzdv.mtocp.mapper.DepotMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class GetDepotByNameUseCase(
    private val depotRepository: DepotRepository
) {
    suspend operator fun invoke(name: String, isDinner: Boolean): DepotDomain {
        return withContext(Dispatchers.IO) {
            depotRepository.all.map { depot -> DepotMapper.fromJoinModelToDomain(depot) }
                .filter {
                    when {
                        !isDinner -> it.dinnerDepot == isDinner || it.dinnerDepot == null
                        else -> it.dinnerDepot == isDinner
                    }
                }
                .firstOrNull { depotDomain ->
                    depotDomain.name.lowercase(Locale.getDefault()) == name.lowercase(
                        Locale.getDefault()
                    )
                }
                ?: throw IllegalArgumentException("Depot $name not found")
        }
    }
}