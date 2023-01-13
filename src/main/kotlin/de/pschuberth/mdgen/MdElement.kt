package de.pschuberth.mdgen

sealed class MdElement(private val md: Md) {
    protected open val level: Int = 0

    fun section(sectionLevel: Int = level + 1, content: (MdSection.() -> Unit)? = null): MdSection {
        val mdSection = MdSection(md, sectionLevel)
        md.add(mdSection)
        content?.invoke(mdSection)
        return mdSection
    }

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
