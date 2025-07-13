package org.pointyware.ember.entities.tensors

import org.pointyware.ember.entities.ObjectPool

/**
 *
 */
class TensorPool: ObjectPool<IntArray, Tensor>() {
    override fun getKeyFromObject(obj: Tensor): IntArray {
        return obj.dimensions
    }

    override fun createObject(key: IntArray): Tensor {
        return Tensor(key)
    }

    override fun onReturn(obj: Tensor) {
        obj.zero()
    }
}
