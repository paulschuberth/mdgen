package de.pschuberth.util

/**
 * Wrapper class around [Int] that ensures the value inside can only increase, but never decrease of be reassigned the
 * same Int.
 *
 * @param initialValue The initial value of the wrapped Int.
 */
class IncreasingInt(initialValue: Int = 0) {

    /**
     * The current value. It's setter only applies the new value if it is bigger than the current.
     */
    var value = initialValue
        set(newValue) {
            if (newValue > field) {
                field = newValue
            }
        }
}
