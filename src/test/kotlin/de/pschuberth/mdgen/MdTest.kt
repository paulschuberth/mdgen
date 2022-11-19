package de.pschuberth.mdgen

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.Charset
import kotlin.test.assertEquals

class MdTest {

    private lateinit var file: File
    private lateinit var md: Md

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

    @Test
    fun `Can generate # Title`() {
        // Act
        md.start {
            section { +"Hello World" }
        }
        md.render()

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals("# Hello World", fileContent)
    }

    @Test
    fun `Can create # Title with paragraph`() {
        // Act
        md.start {
            section {
                +"Hello World"
                paragraph("I am a paragraph.")
            }
        }.render()

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            # Hello World
            I am a paragraph.
            """.trimIndent(),
            fileContent
        )
    }

    @Test
    fun `Can chain paragraphs`() {
        // Act
        md.start {
            paragraph("First paragraph.")
            paragraph("Second paragraph.")
        }.render()

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            First paragraph.
            
            Second paragraph.
            """.trimIndent(),
            fileContent
        )
    }

    @Test
    fun `Can create nested ## Subtitle`() {
        // Act
        md.start {
            section {
                +"Hello World"
                section { +"Subsection" }
            }
        }.render()
        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            # Hello World
            ## Subsection
            """.trimIndent(),
            fileContent
        )
    }

    @Test
    fun `Can create nested ### Subsubtitle`() {
        // Act
        md.start {
            section {
                +"Hello World"
                section {
                    +"Subsection"
                    section {
                        +"Subsubsection"
                    }
                }
            }
        }.render()
        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            # Hello World
            ## Subsection
            ### Subsubsection
            """.trimIndent(),
            fileContent
        )
    }
}
