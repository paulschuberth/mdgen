package de.pschuberth.mdgen

import java.io.OutputStream

class Md(private val outputStream: OutputStream) {

    private val elements: MutableList<MdElement> = mutableListOf()

    fun start(content: MdElement.() -> Unit): Md {
        content(EmptyStartElement(this))
        return this
    }

    fun add(element: MdElement) {
        elements.add(element)
    }

    fun render() {
        if (elements.isNotEmpty()) {
            outputStream.write(elements.first().toString().toByteArray())
        }
        elements.zipWithNext().forEach { pair ->
            var separator = "\n"
            val previous = pair.first
            val current = pair.second

            if (previous is Paragraph && current is Paragraph) {
                separator = "\n\n"
            }
            if (previous is Paragraph && current is Section) {
                separator = "\n\n"
            }
            outputStream.write("$separator$current".toByteArray())
        }
    }
}

sealed class MdElement(private val md: Md) {
    protected open val level: Int = 0

    fun section(title: String, content: (Section.() -> Unit)? = null): Section {
        val section = Section(md, title, level + 1)
        md.add(section)
        content?.invoke(section)
        return section
    }

    fun paragraph(content: String): Paragraph {
        val paragraph = Paragraph(md, content)
        md.add(paragraph)
        return paragraph
    }
}

class Section(
    md: Md,
    private val title: String,
    override var level: Int
) : MdElement(md) {
    override fun toString(): String {
        return "${"#".times(level)} $title"
    }
}

class Paragraph(
    md: Md,
    val content: String
) : MdElement(md) {
    override fun toString(): String {
        return content
    }
}

class EmptyStartElement(md: Md) : MdElement(md)

private fun String.times(count: Int): String {
    var result = ""
    for (i in 0 until count) {
        result = result.plus(this)
    }
    return result
}
