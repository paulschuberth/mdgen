package de.pschuberth.mdgen

class MdTable(md: Md, private val headers: Array<out MdTableHeader>, body: (MdTable.() -> Unit)?) : MdElement(md) {

    private val rows = mutableListOf<MdTableRow>()

    init {
        body?.invoke(this)
    }

    override fun toString(): String {
        return buildString {
            for (header in headers) {
                append("| ${header.content} ")
            }
            append("|\n")
            for (header in headers) {
                val alignmentString = when (header.alignment) {
                    MdTableHeader.Alignment.CENTER -> "| --- "
                    MdTableHeader.Alignment.LEFT -> "| :--- "
                    MdTableHeader.Alignment.RIGHT -> "| ---: "
                }
                append(alignmentString)
            }
            append("|\n")
            for (row in rows) {
                append(row)
            }
        }
    }

    fun row(content: MdTableRow.() -> Unit) {
        val row = MdTableRow()
        row.content()
        rows.add(row)
    }
}

class MdTableHeader constructor(
    val content: String,
    val alignment: Alignment
) {
    enum class Alignment {
        LEFT, CENTER, RIGHT
    }
}

class MdTableRow {
    private val cells = mutableListOf<String>()
    operator fun String.unaryPlus(): MdTableRow {
        cells.add(this)
        return this@MdTableRow
    }

    operator fun plus(cell: String): MdTableRow {
        cells.add(cell)
        return this@MdTableRow
    }


    override fun toString(): String {
        return buildString {
            for (cell in cells) {
                append("| $cell ")
            }
            append("|\n")
        }
    }
}
