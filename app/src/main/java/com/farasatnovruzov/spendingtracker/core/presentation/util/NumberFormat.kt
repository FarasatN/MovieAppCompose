package com.farasatnovruzov.spendingtracker.core.presentation.util

import java.math.BigDecimal
import java.util.Locale

// 12.0 -> "12", 12.5 -> "12.5", 1.0E7 -> "10000000" (elmi yazılışsız)
fun Double.toPlainText(): String =
    BigDecimal.valueOf(this).stripTrailingZeros().toPlainString()

// Mətn sahəsi üçün: 0.0 -> "" (sahə boş görünsün)
fun Double.toInputText(): String = if (this == 0.0) "" else toPlainText()

// Pul göstərişi üçün: həmişə 2 onluq rəqəm (12.700000000000003 -> "12.70")
fun Double.toMoney(): String = String.format(Locale.US, "%.2f", this)

// "" -> 0.0 (boş sahə sıfır sayılır), "5." -> 5.0, "." -> null (yanlış)
fun String.toAmountOrNull(): Double? = if (isEmpty()) 0.0 else toDoubleOrNull()

private val decimalRegex = Regex("""^\d{0,9}(\.\d{0,3})?$""")

// Klaviaturadan gələn mətni yoxlayır. Etibarlıdırsa təmizlənmiş mətni, yoxdursa null qaytarır.
fun filterDecimalInput(raw: String): String? {
    val text = raw.replace(',', '.')   // bəzi klaviaturalar vergül verir
    return text.takeIf { it.matches(decimalRegex) }
}
