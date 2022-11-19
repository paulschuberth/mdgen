package de.pschuberth.mdgen // ktlint-disable filename

fun String.times(count: Int): String {
    var result = ""
    for (i in 0 until count) {
        result = result.plus(this)
    }
    return result
}
