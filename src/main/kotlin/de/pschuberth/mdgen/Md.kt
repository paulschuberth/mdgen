package de.pschuberth.mdgen

import java.io.OutputStream

class Md(private val outputStream: OutputStream) {

    private val elements: MutableList<MdElement> = mutableListOf()

    fun start(content: MdElement.() -> Unit) {
        content(EmptyStartElement(this))
        render()
    }

    fun add(element: MdElement) {
        elements.add(element)
    }

    private fun render() {
        if (elements.isNotEmpty()) {
            outputStream.write(elements.first().toString().toByteArray())
        }
        elements.zipWithNext().forEach { pair ->
            var separator = "\n"
            val previous = pair.first
            val current = pair.second

            if (previous is MdParagraph) {
                separator = "\n\n"
            }

            if (previous is MdSection) {
                separator = "\n\n"
            }
            outputStream.write("$separator$current".toByteArray())
        }
        // Add trailing newline
        outputStream.write("\n".toByteArray())
    }
}
