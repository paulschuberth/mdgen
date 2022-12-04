package de.pschuberth.mdgen

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import kotlin.test.assertEquals

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

    protected fun fileContent() = this.fileContent(file)
    protected fun fileContent(file: File) = file.readText(Charset.defaultCharset())
    protected fun assertFileContentIs(expectedContent: String) {
        assertEquals(expectedContent, fileContent().trim())
    }
}
