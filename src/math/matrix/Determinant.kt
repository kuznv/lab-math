package math.matrix

fun Matrix.determinant(): Cell {
    toTriangular()
    return foldIndexed(Cell.ONE) { i, product, row -> product * row[i] }
}