package de.pschuberth.util

import de.pschuberth.mdgen.Alignment
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals

class PaddedStringTest {

    @Test
    fun `Pads value with spaces for even remainder`() {
        val paddedString = PaddedString(value = "Foo", length = 7)
        assertEquals("  Foo  ", paddedString.toString())
    }

    @Test
    fun `Pads value with spaces for odd remainder`() {
        val paddedString = PaddedString(value = "Fooo", length = 7)
        assertEquals(" Fooo  ", paddedString.toString())
    }

    @Test
    fun `Can use fixed length prefix`() {
        val paddedString = PaddedString(value = "Foo", length = 7, fixedLength = 1, Alignment.LEFT)
        assertEquals(" Foo   ", paddedString.toString())
    }

    @Test
    fun `Unspecfied alignment uses left as default`() {
        val paddedString = PaddedString(value = "Foo", length = 7, fixedLength = 1)
        assertEquals(" Foo   ", paddedString.toString())
    }

    @Test
    fun `Can use fixed length postfix`() {
        val paddedString = PaddedString(value = "Foo", length = 7, fixedLength = 1, alignment = Alignment.RIGHT)
        assertEquals("   Foo ", paddedString.toString())
    }

    @Test
    fun `Constructor throws IllegalArgumentException if length of value is longer than given length`() {
        assertThrows<IllegalArgumentException> {
            PaddedString(
                value = "Foo Bar Baz",
                length = 2
            )
        }
    }

    @Test
    fun `Constructor throws IllegalArgumentException if length of value is longer than given length plus prefixLength`() {
        assertThrows<IllegalArgumentException> {
            PaddedString(
                value = "Foo",
                length = 3,
                fixedLength = 1,
                alignment = Alignment.LEFT
            )
        }
    }

    @Test
    fun `Constructor throws IllegalArgumentException if length of value is longer than given length plus postfixLength`() {
        assertThrows<IllegalArgumentException> {
            PaddedString(
                value = "Foo",
                length = 3,
                fixedLength = 1,
                alignment = Alignment.RIGHT
            )
        }
    }
}
