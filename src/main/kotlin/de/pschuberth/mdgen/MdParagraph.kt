package de.pschuberth.mdgen

class MdParagraph(
    md: Md,
    val content: String
) : MdElement(md) {
    override fun toString(): String {
        return content
    }
}
