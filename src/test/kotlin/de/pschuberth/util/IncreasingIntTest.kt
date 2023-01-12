package de.pschuberth.util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class IncreasingIntTest {

    @Test
    fun `Uses initial value`() {
        val int = IncreasingInt(23)
        assertEquals(23, int.value)
    }

    @Test
    fun `Does not decrease value`() {
        val int = IncreasingInt(23)
        int.value = 13
        assertEquals(23, int.value)
    }

    @Test
    fun `Does increase value`() {
        val int = IncreasingInt(23)
        int.value = 42
        assertEquals(42, int.value)
    }
}
