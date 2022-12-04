package de.pschuberth.mdgen

import de.pschuberth.mdgen.MdTableHeader.Alignment.*
import org.junit.jupiter.api.Test

class MdTableTest : MdTestCase() {

    @Test
    fun `Can create simple table header`() {

        md.start {
            table("A")
        }

        assertFileContentIs(
            """
            | A |
            | --- |
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
            | A | B | C |
            | --- | --- | --- |
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
            | :--- |
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
            | ---: |
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
            | --- |
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
            | :--- | --- | ---: |
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
            | :--- |
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
            | --- |
            | World |
            """.trimIndent()
        )
    }

    @Test
    fun `Can create table with two columns and one content row`() {
        md.start {
            table("Hello", "World") {
                row {
                    +"Foo"
                    +"Bar"
                }
            }
        }
        assertFileContentIs(
            """
            | Hello | World |
            | --- | --- |
            | Foo | Bar |
            """.trimIndent()
        )
    }
}
