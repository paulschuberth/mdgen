package de.pschuberth.util

import de.pschuberth.mdgen.Alignment

class PaddedString(
    private val value: String,
    private val length: Int,
    private val fixedLength: Int? = null,
    private val alignment: Alignment = Alignment.LEFT
) {

    init {

        if (value.length == length && fixedLength != null) {
            throw IllegalArgumentException("Length ($length) must be greater than length of value ($value, $length) plus prefix length ($fixedLength)")
        }

        if (value.length > length) {
            throw IllegalArgumentException("Length ($length) must be greater than length of value ($value, $length)")
        }
    }

    override fun toString(): String {
        val remainder = length - value.length
        val firstPadding = lengthOfFirstPadding(remainder)
        val secondPadding = lengthOfSecondPadding(remainder)
        return buildString {
            append(" ".repeat(firstPadding))
            append(value)
            append(" ".repeat(secondPadding))
        }
    }

    private fun lengthOfFirstPadding(remainder: Int): Int {
        if (fixedLength != null && alignment == Alignment.LEFT) {
            return fixedLength
        }
        if (fixedLength != null && alignment == Alignment.RIGHT) {
            return remainder - fixedLength
        }
        return (remainder / 2)
    }

    private fun lengthOfSecondPadding(remainder: Int): Int {
        if (fixedLength != null && alignment == Alignment.LEFT) {
            return remainder - 1
        }
        if (fixedLength != null && alignment == Alignment.RIGHT) {
            return fixedLength
        }
        return if (remainder % 2 == 0) {
            remainder / 2
        } else {
            (remainder / 2) + 1
        }
    }
}
