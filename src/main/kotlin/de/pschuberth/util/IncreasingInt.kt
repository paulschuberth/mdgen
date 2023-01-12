package de.pschuberth.util

class IncreasingInt(initialValue: Int = 0) {
    var value = initialValue
        set(newValue) {
            if (newValue > field) {
                field = newValue
            }
        }
}
