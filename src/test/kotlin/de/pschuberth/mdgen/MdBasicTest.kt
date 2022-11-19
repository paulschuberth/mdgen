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
        assertEquals("# Hello World", fileContent())
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
        assertEquals(
            """
            #### Hello
            """.trimIndent(),
            fileContent()
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
        }

        // Assert
        assertEquals(
            """
            # Hello World
            I am a paragraph.
            """.trimIndent(),
            fileContent()
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
        assertEquals(
            """
            I am a paragraph.
            
            # Hello World
            """.trimIndent(),
            fileContent()
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
        assertEquals(
            """
            First paragraph.
            
            Second paragraph.
            """.trimIndent(),
            fileContent()
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
        assertEquals(
            """
            # Hello World
            ## Subsection
            """.trimIndent(),
            fileContent()
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
        assertEquals(
            """
            # Hello World
            ## Subsection
            ### Subsubsection
            """.trimIndent(),
            fileContent()
        )
    }
}
