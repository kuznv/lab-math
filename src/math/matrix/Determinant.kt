package math.matrix

import util.isOdd

fun Matrix.determinant(): Cell = when (size) {
    0 -> Cell.ZERO
    1 -> this[0][0]
    2 -> this[0][0] * this[1][1] - this[0][1] * this[1][0]
    else -> {
        var sum = Cell.ZERO
        for (i in indices) {
            val cell = this[0][i]
            val term = if (i.isOdd) cell else cell.negate()
            sum += term * minor(0, i)
        }
        sum
    }
}/*.also { println("determinant of $this = $it") }*/

fun Matrix.minor(row: Int, column: Int) = remove(row, column).determinant()

@Suppress("UNCHECKED_CAST")
fun Matrix.remove(row: Int, column: Int) = Matrix(
        RemoveList(
                mapTo(ArrayList(columnsCount - 1)) { RemoveList(it, column) },
                row
        )
)

class RemoveList<E>(private val delegate: MutableList<E>, private val removedIndex: Int) : AbstractMutableList<E>() {
    private fun newIndex(index: Int) = index.takeIf { it < removedIndex } ?: index + 1

    override fun add(index: Int, element: E) { delegate.add(newIndex(index), element) }

    override fun removeAt(index: Int): E = delegate.removeAt(newIndex(index))

    override fun set(index: Int, element: E): E = delegate.set(newIndex(index), element)

    override val size = delegate.size - 1

    override fun get(index: Int): E = delegate[newIndex(index)]
}