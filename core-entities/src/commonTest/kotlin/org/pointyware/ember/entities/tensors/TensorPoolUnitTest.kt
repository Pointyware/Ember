package org.pointyware.ember.entities.tensors

import kotlin.test.Test

class TensorPoolUnitTest {
    @Test
    fun test_same_dimensions_return_same_object() {
        val pool = TensorPool()
        val tensor1 = pool.getObject(listOf(1, 2, 3))
        pool.returnObject(tensor1)

        val tensor2 = pool.getObject(listOf(1, 2, 3))
        assert(tensor1 === tensor2) { "Returned objects should be reused when possible" }
        val tensor3 = pool.getObject(listOf(1, 2, 4))
        assert(tensor1 !== tensor3) { "Returned objects should not be reused after being retrieved" }
    }
}
