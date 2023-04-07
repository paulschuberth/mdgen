package de.pschuberth.mdgen

class MdCodeBlock(
    md: Md,
    private val language: String,
    private val code: String,
) : MdElement(md) {

    override fun toString(): String {
        return """
```$language
${code.trimIndent()}
```
        """.trimIndent()
    }
}
