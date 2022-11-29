package de.pschuberth.mdgen

import org.junit.jupiter.api.Test

class MdCodeBlockTest : MdTestCase() {
    @Test
    fun `Can render fenced code block using backticks`() {
        // Act
        md.start {
            code {
                """
                    fun foo(): String {
                        return "bar"
                    }
                """
            }
        }

        // Assert
        assertFileContentIs(
            """
            ```
            fun foo(): String {
                return "bar"
            }
            ```
            """.trimIndent()
        )
    }

    @Test
    fun `Can render fenced code block using backticks including the specfified language`() {
        // Act
        md.start {
            code("kotlin") {
                """
                    fun foo(): String {
                        return "bar"
                    }
                """
            }
        }

        // Assert
        assertFileContentIs(
            """
            ```kotlin
            fun foo(): String {
                return "bar"
            }
            ```
            """.trimIndent()
        )
    }
}
