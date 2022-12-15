package de.pschuberth.mdgen

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MdBasicTest : MdTestCase() {

    @Test
    fun `Can generate # Title`() {
        // Act
        md.start {
            section { +"Hello World" }
        }

        // Assert
        assertFileContentIs("# Hello World")
    }

    @Test
    fun `Can generate section with specified nesting`() {
        // Act
        md.start {
            section(4) {
                +"Hello"
            }
        }

        // Assert
        assertFileContentIs("#### Hello")
    }

    @Test
    fun `Adds two new lines after heading`() {
        // Act
        md.start {
            section {
                +"Hello World"
                paragraph("I am a paragraph.")
            }
        }

        // Assert
        assertFileContentIs(
            """
            # Hello World
            
            I am a paragraph.
            """.trimIndent()
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
        }

        // Assert
        assertFileContentIs(
            """
            I am a paragraph.
            
            # Hello World
            """.trimIndent()
        )
    }

    @Test
    fun `Adds two new lines between paragraph and next list`() {
        // Act
        md.start {
            paragraph("I am a paragraph.")
            list {
                -"List Item"
            }
        }

        // Assert
        assertFileContentIs(
            """
            I am a paragraph.
            
            - List Item
            """.trimIndent()
        )
    }

    @Test
    fun `Can chain paragraphs`() {
        // Act
        md.start {
            paragraph("First paragraph.")
            paragraph("Second paragraph.")
        }

        // Assert
        assertFileContentIs(
            """
            First paragraph.
            
            Second paragraph.
            """.trimIndent()
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
        }
        // Assert
        assertFileContentIs(
            """
            # Hello World
            
            ## Subsection
            """.trimIndent()
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
        }
        // Assert
        assertFileContentIs(
            """
            # Hello World
            
            ## Subsection
            
            ### Subsubsection
            """.trimIndent()
        )
    }

    @Test
    fun `Adds no newline in the end`() {
        md.start { }
        assertEquals(
            "",
            fileContent()
        )
    }
}
