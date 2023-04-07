package de.pschuberth.mdgen

/**
 * Base class for Markdown elements handled by mdgen methods. It provides factory methods for all supported top-level
 * elements.
 */
sealed class MdElement(private val md: Md) {

    /**
     * Level of nesting of the element. Currently only in use for the sections.
     */
    protected open val level: Int = 0

    /**
     * Factory method for an [MdSection].
     *
     * @param sectionLevel The level of the section heading. Specifically the number of # used in the Markdown heading.
     * @param content Lamda with receiver of type [MdSection]. Provides access to the instance methods of MdSection.
     * @return New instance of [MdSection].
     */
    fun section(sectionLevel: Int = level + 1, content: (MdSection.() -> Unit)? = null): MdSection {
        val mdSection = MdSection(md, sectionLevel)
        md.add(mdSection)
        content?.invoke(mdSection)
        return mdSection
    }

    /**
     * Factory method for an [MdParagraph].
     *
     * @param content The content of the paragraph.
     */
    fun paragraph(content: String): MdParagraph {
        val mdParagraph = MdParagraph(md, content)
        md.add(mdParagraph)
        return mdParagraph
    }

    open fun list(content: MdList.() -> Unit): MdList {
        val mdList = MdList(md)
        content.invoke(mdList)
        md.add(mdList)
        return mdList
    }

    /**
     * Inserts a [MdCodeBlock] into the current position of the Markdown document.
     *
     * @param language The programming language of the code block. The markdown renderer might use it for correct syntax
     * highlighting.
     * @param content Lambda returning the visible content of the code block as a [String].
     *
     */
    fun code(language: String = "", content: () -> String) {
        val mdCodeBlock = MdCodeBlock(md, language, content.invoke())
        md.add(mdCodeBlock)
    }

    fun table(vararg headlines: Pair<String, Alignment>, body: (MdTable.() -> Unit)? = null) {
        val headers = buildList {
            for (headline in headlines) {
                add(MdTableHeader(headline.first, headline.second))
            }
        }
        val table = MdTable(md, headers.toTypedArray(), body)
        md.add(table)
    }

    fun table(vararg headlines: String, body: (MdTable.() -> Unit)? = null) {
        val headers = buildList {
            for (headline in headlines) {
                add(MdTableHeader(headline))
            }
        }
        val table = MdTable(md, headers.toTypedArray(), body)
        md.add(table)
    }
}
