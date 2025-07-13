package org.pointyware.ember.entities

/**
 * A simple object pool that retains objects returned to it and provides objects requested,
 * creating them when necessary.
 */
abstract class ObjectPool<T: Any, K: Any> {
    
    private val pool = mutableMapOf<K, MutableList<T>>()
    
    abstract fun getKeyFromObject(obj: T): K
    abstract fun createObject(key: K): T

    fun getObject(key: K): T {
        return pool[key]?.let { list ->
            list.removeAt(list.lastIndex).also {
                if (list.isEmpty()) pool.remove(key)
            }
        } ?: createObject(key)
    }
    fun returnObject(obj: T) {
        val key = getKeyFromObject(obj)
        pool.getOrPut(key) {
            mutableListOf()
        }.let { list ->
            list.add(list.lastIndex, obj)
        }
    }
}
