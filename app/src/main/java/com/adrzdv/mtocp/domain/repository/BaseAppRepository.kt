package com.adrzdv.mtocp.domain.repository

/**
 * Base repository interface defining common data access operations.
 *
 * @param V The type of the entity/value being managed.
 * @param K The type of the unique identifier/key for the entity.
 */
interface BaseAppRepository<V, K> {
    suspend fun getAll(): List<V>
    suspend fun getEntity(id: K): V
    suspend fun saveAll(entities: List<V>)
}