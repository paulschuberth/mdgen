package de.pschuberth.mdgen

import de.pschuberth.util.IncreasingInt
import de.pschuberth.util.PaddedString

class MdTable(md: Md, private val headers: Array<out MdTableHeader>, body: (MdTable.() -> Unit)?) : MdElement(md) {

    private val rows = mutableListOf<MdTableRow>()

    init {
        body?.invoke(this)
    }

    override fun toString(): String {
        return buildString {
            val maxWidths = maxWidthPerColumn()
            for (i in headers.indices) {
                val header = headers[i]
                append(
                    "|${
                    PaddedString(
                        value = header.content,
                        length = maxWidths[i] + 2,
                        fixedLength = 1
                    )
                    }"
                )
            }
            append("|\n")
            for (i in headers.indices) {
                val header = headers[i]
                val alignmentString = header.alignmentStringFor(maxWidth = maxWidths[i])
                append("|$alignmentString")
            }
            append("|\n")
            for (row in rows) {
                append(row.toString(withLength = maxWidths, andAlignments = headers.alignments()))
            }
        }
    }

    fun row(content: MdTableRow.() -> Unit) {
        val row = MdTableRow()
        row.content()
        rows.add(row)
    }

    fun maxWidthPerColumn(): List<Int> {
        return buildList {
            for (i in headers.indices) {
                val currentMax = IncreasingInt(3)
                currentMax.value = headers[i].content.length
                for (row in rows) {
                    val cell = row.cells[i]
                    currentMax.value = cell.length
                }
                add(currentMax.value)
            }
        }
    }
}

private fun Array<out MdTableHeader>.alignments(): List<Alignment> {
    return this.map { it.alignment }.toList()
}

enum class Alignment {
    LEFT, CENTER, RIGHT, UNSPECIFIED
}

class MdTableHeader constructor(
    val content: String,
    val alignment: Alignment
) {

    fun alignmentStringFor(maxWidth: Int) = when (alignment) {
        Alignment.CENTER -> ":${"-".repeat(maxWidth)}:"
        Alignment.LEFT -> ":${"-".repeat(maxWidth + 1)}"
        Alignment.RIGHT -> "${"-".repeat(maxWidth + 1)}:"
        Alignment.UNSPECIFIED -> "-".repeat(maxWidth + 2)
    }
}

class MdTableRow {
    val cells = mutableListOf<String>()
    operator fun String.unaryPlus(): MdTableRow {
        cells.add(this)
        return this@MdTableRow
    }

    operator fun plus(cell: String): MdTableRow {
        cells.add(cell)
        return this@MdTableRow
    }

    fun toString(withLength: List<Int>, andAlignments: List<Alignment>): String {
        return buildString {
            for (i in cells.indices) {
                val cell = cells[i]
                append(
                    "|${
                    PaddedString(
                        value = cell,
                        length = withLength[i] + 2,
                        fixedLength = 1,
                        alignment = andAlignments[i]
                    )
                    }"
                )
            }
            append("|\n")
        }
    }
}
