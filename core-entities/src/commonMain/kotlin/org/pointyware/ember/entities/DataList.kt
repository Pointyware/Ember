package org.pointyware.ember.entities

/**
 * @param I The type of independent variable.
 * @param D The type of dependent variable.
 */
interface DataList<I, D> {
    val label: String
    val size: Int
    val start: I
    val end: I
    val min: D
    val max: D
    fun put(index: I, data: D)
    fun view(start: I, end: I): Map<I, D>
}

data class ObjDataList<I: Comparable<I>, D: Comparable<D>>(
    override val label: String,
    val initialStart: I,
    val initialEnd: I,
    val initialMin: D,
    val initialMax: D
): DataList<I, D> {
    private val data: MutableMap<I, D> = mutableMapOf()
    override val size: Int get() = data.size

    override var start: I = initialStart
        private set
    override var end: I = initialEnd
        private set
    override var min: D = initialMin
        private set
    override var max: D = initialMax
        private set

    override fun put(index: I, data: D) {
        // Domain Check
        if (index < start) start = index
        else if (index > end) end = index
        // Range Check
        if (data < min) min = data
        else if (data > max) max = data

        this.data[index] = data
    }

    override fun view(start: I, end: I): Map<I, D> {
        val range = start..end
        return data.filter { (key, _) -> key in range }
    }
}

/**
 * @param label The label of the data list.
 * @param start The range start reported when data does not lie outside the domain.
 * @param end The range end reported when data does not lie outside the domain.
 * @param min The minimum value reported when data does not lie outside the range.
 * @param max The maximum value reported when data does not lie outside the range.
 */
inline fun<reified I: Comparable<I>, reified D: Comparable<D>> dataList(
    label: String,
    start: I, end: I,
    min: D, max: D
): ObjDataList<I, D> {
    return ObjDataList(label, start, end, min, max)
}
