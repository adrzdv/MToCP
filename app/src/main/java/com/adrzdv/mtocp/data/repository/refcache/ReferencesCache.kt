package com.adrzdv.mtocp.data.repository.refcache

/**
 * A generic in-memory cache for storing and retrieving reference data.
 *
 * This class provides a simple wrapper around a [Map] to store objects of type [V]
 * indexed by a unique key of type [K]. It is typically used to hold static or
 * slow-changing reference data that needs to be accessed quickly by ID.
 *
 * @param K The type of the key used to identify items in the cache.
 * @param V The type of the items stored in the cache.
 */
class ReferencesCache<K, V> {
    private var map: Map<K, V> = emptyMap()

    fun set(items: List<V>, keySelector: (V) -> K) {
        map = items.associateBy(keySelector)
    }

    fun get(key: K): V {
        return map[key] ?: throw IllegalArgumentException("Reference not found: $key")
    }

    fun getAll(): List<V> {
        return map.values.toList()
    }

    fun isEmpty(): Boolean {
        return map.isEmpty()
    }

}