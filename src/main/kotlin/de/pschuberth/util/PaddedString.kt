package de.pschuberth.util

import de.pschuberth.mdgen.Alignment

/**
 * A space padded [String] with [value] as content.
 *
 * If [prefixLength] is not null and [alignment] is either
 * [Alignment.LEFT] or [Alignment.RIGHT] the final String starts or ends with [prefixLength] spaces. It assumes LTR
 * reading. The remaining spaces are put after [value]. If [alignment] is equal to [Alignment.CENTER] the prefixLength is ignored.
 *
 * @exception IllegalArgumentException if the length of the [value] is bigger than the length of the [PaddedString] itself.
 * @exception IllegalArgumentException if the length of [value] is equal to [length] and [prefixLength] is not null.
 *
 */
class PaddedString constructor(
    private val value: String,
    private val length: Int,
    private val prefixLength: Int? = null,
    private val alignment: Alignment = Alignment.LEFT,
) {

    init {
        if (value.length == length && prefixLength != null) {
            throw IllegalArgumentException("Length ($length) must be greater than length of value ($value, $length) plus prefix length ($prefixLength)")
        }

        if (value.length > length) {
            throw IllegalArgumentException("Length ($length) must be greater than length of value ($value, $length)")
        }
    }

    /**
     * Builds a new String that matches the requirements described by the constructor parameters.
     */
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
        if (prefixLength != null && alignment == Alignment.LEFT) {
            return prefixLength
        }
        if (prefixLength != null && alignment == Alignment.RIGHT) {
            return remainder - prefixLength
        }
        return (remainder / 2)
    }

    private fun lengthOfSecondPadding(remainder: Int): Int {
        if (prefixLength != null && alignment == Alignment.LEFT) {
            return remainder - 1
        }
        if (prefixLength != null && alignment == Alignment.RIGHT) {
            return prefixLength
        }
        return if (remainder % 2 == 0) {
            remainder / 2
        } else {
            (remainder / 2) + 1
        }
    }
}
