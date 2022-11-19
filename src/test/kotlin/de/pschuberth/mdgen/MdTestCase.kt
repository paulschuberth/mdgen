package de.pschuberth.mdgen

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset

abstract class MdTestCase {
    protected lateinit var file: File
    protected lateinit var md: Md

    @BeforeEach
    fun setup() {
        file = File.createTempFile("MdTest", ".md")
        val outputStream = FileOutputStream(file)
        md = Md(outputStream)
    }

    @AfterEach
    fun cleanup() {
        if (file.exists()) {
            file.delete()
        }
    }

    protected fun fileContent() = file.readText(Charset.defaultCharset())
}
