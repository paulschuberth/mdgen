package de.pschuberth.mdgen

import org.junit.jupiter.api.Test
import java.nio.charset.Charset
import kotlin.test.assertEquals

class MdListTest : MdTestCase() {

    @Test
    fun `Can create unordered list`() {
        md.start {
            list {
                -"First"
                -"Second"
                -"Third"
            }
        }

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            - First
            - Second
            - Third
            """.trimIndent(),
            fileContent
        )
    }

    @Test
    fun `Can create ordered list`() {
        md.start {
            list {
                +"First"
                +"Second"
                +"Third"
            }
        }

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            1. First
            2. Second
            3. Third
            """.trimIndent(),
            fileContent
        )
    }

    @Test
    fun `Can set initial value for ordered list`() {
        md.start {
            list {
                setIndex(23)
                +"First"
                +"Second"
                +"Third"
            }
        }

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            23. First
            24. Second
            25. Third
            """.trimIndent(),
            fileContent
        )
    }

    @Test
    fun `Can create nested ordered list in unordered list`() {
        md.start {
            list {
                -"First"
                -"Second"
                list {
                    +"First nested"
                    +"Second nested"
                }
                -"Third"
            }
        }

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            - First
            - Second
                1. First nested
                2. Second nested
            - Third
            """.trimIndent(),
            fileContent
        )
    }

    @Test
    fun `Can create nested unordered list in ordered list`() {
        md.start {
            list {
                +"First"
                +"Second"
                list {
                    -"First nested"
                    -"Second nested"
                }
                +"Third"
            }
        }

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            1. First
            2. Second
                - First nested
                - Second nested
            3. Third
            """.trimIndent(),
            fileContent
        )
    }

    @Test
    fun `Can create nested unordered list in unordered list`() {
        md.start {
            list {
                -"First"
                -"Second"
                list {
                    -"First nested"
                    -"Second nested"
                }
                -"Third"
            }
        }

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            - First
            - Second
                - First nested
                - Second nested
            - Third
            """.trimIndent(),
            fileContent
        )
    }

    @Test
    fun `Can create nested ordered list in ordered list`() {
        md.start {
            list {
                +"First"
                +"Second"
                list {
                    +"First nested"
                    +"Second nested"
                }
                +"Third"
            }
        }

        // Assert
        val fileContent = file.readText(Charset.defaultCharset())
        assertEquals(
            """
            1. First
            2. Second
                1. First nested
                2. Second nested
            3. Third
            """.trimIndent(),
            fileContent
        )
    }
}
