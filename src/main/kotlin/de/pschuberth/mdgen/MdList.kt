package de.pschuberth.mdgen

import de.pschuberth.mdgen.MdList.MdListElement.Companion.o
import de.pschuberth.mdgen.MdList.MdListElement.Companion.u

class MdList(
    private val md: Md,
    override val level: Int = 0
) : MdElement(md) {

    private var initialValue: Int = 1

    companion object {
        private const val DEFAULT_INDENT = "    "
    }

    private val listElements = mutableListOf<MdElement>()

    operator fun String.unaryMinus() {
        this@MdList.listElements.add(u(md, this))
    }

    operator fun String.unaryPlus() {
        val countOfListElements = listElements.count { it is MdListElement }
        val nextPosition = if (initialValue == 1) {
            countOfListElements + 1
        } else {
            countOfListElements + initialValue
        }
        this@MdList.listElements.add(o(md, this, nextPosition))
    }

    override fun list(content: MdList.() -> Unit): MdList {
        val mdNestedList = MdList(md, level + 1)
        content.invoke(mdNestedList)
        listElements.add(mdNestedList)
        return mdNestedList
    }

    override fun toString(): String {
        val builder = StringBuilder()
        val indent = DEFAULT_INDENT.times(level)
        listElements.forEachIndexed { index, listElement ->
            val line = "$indent$listElement"
            if (index == listElements.lastIndex) {
                builder.append(line)
            } else {
                builder.appendLine(line)
            }
        }

        return builder.toString()
    }

    fun setIndex(i: Int) {
        initialValue = i
    }

    sealed class MdListElement(md: Md, override val level: Int) : MdElement(md) {

        companion object {
            fun u(md: Md, content: String): MdUnorderedListElement {
                return MdUnorderedListElement(md, content)
            }

            fun o(md: Md, content: String, position: Int): MdOrderedListElement {
                return MdOrderedListElement(md, content, position)
            }
        }
    }

    class MdOrderedListElement(
        md: Md,
        private val content: String,
        private val position: Int,
        level: Int = 0
    ) :
        MdListElement(md, level) {
        override fun toString(): String {
            return "$position. $content"
        }
    }

    class MdUnorderedListElement(
        md: Md,
        private val content: String,
        level: Int = 0
    ) : MdListElement(md, level) {
        override fun toString(): String {
            return "- $content"
        }
    }
}
