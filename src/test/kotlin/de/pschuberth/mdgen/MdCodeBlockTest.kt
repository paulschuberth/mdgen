package de.pschuberth.mdgen

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

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
        assertEquals(
            """
            ```
            fun foo(): String {
                return "bar"
            }
            ```
            """.trimIndent(),
            fileContent()
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
        assertEquals(
            """
            ```kotlin
            fun foo(): String {
                return "bar"
            }
            ```
            """.trimIndent(),
            fileContent()
        )
    }
}
