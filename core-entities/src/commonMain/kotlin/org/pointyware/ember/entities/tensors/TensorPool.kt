package org.pointyware.ember.entities.tensors

import org.pointyware.ember.entities.ObjectPool

/**
 * A pool of [Tensor] instances. Each tensor is zeroed when it is returned to the pool.
 */
class TensorPool: ObjectPool<List<Int>, Tensor>() {
    override fun getKeyFromObject(obj: Tensor): List<Int> {
        return obj.dimensions.toList()
    }

    override fun createObject(key: List<Int>): Tensor {
        return Tensor(key.toIntArray())
    }

    override fun onReturn(obj: Tensor) {
        obj.zero()
    }
}
