package org.pointyware.ember.training.entities

import kotlin.reflect.KClass

/**
 * Keys which identify a specific element of computation in some context map.
 */
data class ComputationKey<T: Any>(
    val id: Long,
    val type: KClass<T>
) {
}

inline fun <reified T: Any> Long.key(type: KClass<T> = T::class): ComputationKey<T> {
    return ComputationKey(this, type)
}

/**
 *
 */
class ComputationContext() {
    private val map = mutableMapOf<ComputationKey<*>, Any>()

    fun <T: Any> put(key: ComputationKey<T>, value: T) {
        map[key] = value
    }

    fun <T: Any> get(key: ComputationKey<T>): T {
        @Suppress("UNCHECKED_CAST")
        return map[key] as T
    }
}
