package de.pschuberth.mdgen

import de.pschuberth.mdgen.Alignment.CENTER
import de.pschuberth.mdgen.Alignment.LEFT
import de.pschuberth.mdgen.Alignment.RIGHT
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MdTableTest : MdTestCase() {

    @Test
    fun `Can create simple table header`() {
        md.start {
            table("A")
        }

        assertFileContentIs(
            """
            |  A  |
            |-----|
            """.trimIndent()
        )
    }

    @Test
    fun `Can create table header with multiple columns`() {
        md.start {
            table("A", "B", "C")
        }

        assertFileContentIs(
            """
            |  A  |  B  |  C  |
            |-----|-----|-----|
            """.trimIndent()
        )
    }

    @Test
    fun `Can create simple table with left-aligned header`() {
        md.start {
            table("Left" to LEFT)
        }

        assertFileContentIs(
            """
            | Left |
            |:-----|
            """.trimIndent()
        )
    }

    @Test
    fun `Can create simple table with right-aligned header`() {
        md.start {
            table("Right" to RIGHT)
        }

        assertFileContentIs(
            """
            | Right |
            |------:|
            """.trimIndent()
        )
    }

    @Test
    fun `Can create simple table with center-aligned header`() {
        md.start {
            table("Center" to CENTER)
        }

        assertFileContentIs(
            """
            | Center |
            |:------:|
            """.trimIndent()
        )
    }

    @Test
    fun `Can create table header with multiple alignments`() {
        md.start {
            table("Left" to LEFT, "Center" to CENTER, "Right" to RIGHT)
        }
        assertFileContentIs(
            """
            | Left | Center | Right |
            |:-----|:------:|------:|
            """.trimIndent()
        )
    }

    @Test
    fun `Can create table with left aligned header and one content row`() {
        md.start {
            table("Hello" to LEFT) {
                row {
                    +"World"
                }
            }
        }
        assertFileContentIs(
            """
            | Hello |
            |:------|
            | World |
            """.trimIndent()
        )
    }

    @Test
    fun `Can create table with center header and one content row`() {
        md.start {
            table("Hello") {
                row {
                    +"World"
                }
            }
        }
        assertFileContentIs(
            """
            | Hello |
            |-------|
            | World |
            """.trimIndent()
        )
    }

    @Test
    fun `Can create table with two columns and one content row`() {
        md.start {
            table("Hello", "World") {
                row { +"Foo" + "Bar" }
            }
        }
        assertFileContentIs(
            """
            | Hello | World |
            |-------|-------|
            |  Foo  |  Bar  |
            """.trimIndent()
        )
    }

    @Test
    fun `Can add cells to row using infix plus`() {
        md.start {
            table("Hello" to LEFT, "Beautiful" to CENTER, "World" to RIGHT) {
                row {
                    +"Foo" + "Bar" + "Baz"
                }
            }
        }
        assertFileContentIs(
            """
            | Hello | Beautiful | World |
            |:------|:---------:|------:|
            | Foo   |    Bar    |   Baz |
            """.trimIndent()
        )
    }

    @Test
    fun `Determines longest word in each column`() {
        md.start {
            table("1", "2", "3", "longest in column 4") {
                row { +"Fooooooooooo" + "Bar" + "Baz" + "A" }
                row { +"Lorem Ipsum" + "sit amet" + "consectetur adipiscing elit" + "B" }
                row { +"Lala" + "Lorem Ipsum dolor sit" + "adipiscing elit" + "C" }

                assertEquals(listOf(12, 21, 27, 19), this.maxWidthPerColumn())
            }
        }
    }
}
