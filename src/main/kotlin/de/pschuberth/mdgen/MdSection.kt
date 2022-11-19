package de.pschuberth.mdgen

class MdSection(
    md: Md,
    override var level: Int
) : MdElement(md) {
    private var title = ""
    override fun toString(): String {
        return "${"#".times(level)} $title"
    }

    operator fun String.unaryPlus() {
        this@MdSection.title = this
    }
}
