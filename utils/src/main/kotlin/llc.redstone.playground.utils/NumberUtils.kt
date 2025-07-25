package llc.redstone.playground.utils

import java.lang.Double.parseDouble
import java.lang.Integer.parseInt
import java.text.DecimalFormat

fun Double.toInt(): Int {
    return this.toInt()
}

fun Double.toFloat(): Float {
    return this.toFloat()
}

fun String?.toFloaT(): Float {
    if (this == null) return 0f
    try {
        return this.toFloat()
    } catch (e: NumberFormatException) {
        return 0f
    }
}

fun String?.toDoublE(): Double {
    if (this == null) return 0.0
    try {
        return this.toDouble()
    } catch (e: NumberFormatException) {
        return 0.0
    }
}

fun String?.isDouble(): Boolean {
    if (this == null) return false
    try {
        parseDouble(this)
        return true
    } catch (e: NumberFormatException) {
        return false
    }
}

fun formatNumber(value: Double, places: Int): String {
    val suffixes = arrayOf("", "k", "m", "b", "t", "q", "Q", "s", "S", "o", "n", "d")
    var newValue = value
    var suffixIndex = 0

    while (newValue >= 1000 && suffixIndex < suffixes.size - 1) {
        newValue /= 1000
        suffixIndex++
    }

    val format = DecimalFormat("#.${"#".repeat(places)}")
    return format.format(newValue) + suffixes[suffixIndex]
}

fun String?.isInt(): Boolean {
    if (this == null) return false
    try {
        parseInt(this)
        return true
    } catch (e: NumberFormatException) {
        return false
    }
}

fun Int.romanThatBitch(): String {
    val roman = StringBuilder()
    var number = this
    while (number > 0) {
        if (number >= 1000) {
            roman.append("M")
            number -= 1000
        } else if (number >= 900) {
            roman.append("CM")
            number -= 900
        } else if (number >= 500) {
            roman.append("D")
            number -= 500
        } else if (number >= 400) {
            roman.append("CD")
            number -= 400
        } else if (number >= 100) {
            roman.append("C")
            number -= 100
        } else if (number >= 90) {
            roman.append("XC")
            number -= 90
        } else if (number >= 50) {
            roman.append("L")
            number -= 50
        } else if (number >= 40) {
            roman.append("XL")
            number -= 40
        } else if (number >= 10) {
            roman.append("X")
            number -= 10
        } else if (number >= 9) {
            roman.append("IX")
            number -= 9
        } else if (number >= 5) {
            roman.append("V")
            number -= 5
        } else if (number >= 4) {
            roman.append("IV")
            number -= 4
        } else if (number >= 1) {
            roman.append("I")
            number -= 1
        }
    }
    return roman.toString()
}