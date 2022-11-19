package de.pschuberth.mdgen

import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import kotlin.test.assertEquals

class MdBasicTest : MdTestCase() {

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
    fun `Can generate section with specified nesting`() {
        // Act
        md.start {
            section(4) {
                +"Hello"
            }
        }.render()

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            #### Hello
            """.trimIndent(),
            fileContent
        )
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
    fun `Adds two new lines between paragraph and next headline`() {
        // Act
        md.start {
            paragraph("I am a paragraph.")
            section {
                +"Hello World"
            }
        }.render()

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            I am a paragraph.
            
            # Hello World
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
