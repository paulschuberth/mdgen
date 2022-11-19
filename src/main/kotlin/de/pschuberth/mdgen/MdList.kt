package de.pschuberth.mdgen

import de.pschuberth.mdgen.MdList.MdListElement.Companion.o
import de.pschuberth.mdgen.MdList.MdListElement.Companion.u

class MdList(
    md: Md
) : MdElement(md) {

    private val listElements = mutableListOf<MdListElement>()

    operator fun String.unaryMinus() {
        this@MdList.listElements.add(u(this))
    }

    operator fun String.unaryPlus() {
        this@MdList.listElements.add(o(this, listElements.size + 1))
    }

    override fun toString(): String {
        val builder = StringBuilder()

        listElements.forEachIndexed { index, line ->
            if (index == listElements.lastIndex) {
                builder.append(line)
            } else {
                builder.appendLine(line)
            }
        }

        return builder.toString()
    }

    sealed class MdListElement {
        companion object {

            fun u(content: String): MdUnorderedListElement {
                return MdUnorderedListElement(content)
            }

            fun o(content: String, position: Int): MdOrderedListElement {
                return MdOrderedListElement(content, position)
            }
        }
    }

    class MdOrderedListElement(private val content: String, val position: Int) : MdListElement() {
        override fun toString(): String {
            return "$position. $content"
        }
    }

    class MdUnorderedListElement(private val content: String) : MdListElement() {
        override fun toString(): String {
            return "- $content"
        }
    }
}
