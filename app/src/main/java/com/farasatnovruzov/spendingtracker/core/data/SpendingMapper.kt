package com.farasatnovruzov.spendingtracker.core.data

import com.farasatnovruzov.spendingtracker.core.data.local.SpendingEntity
import com.farasatnovruzov.spendingtracker.core.domain.Spending
import java.time.Instant
import java.time.ZoneId

fun SpendingEntity.toSpending(): Spending = Spending(
    spendingId = spendingId ?: 0,
    name = name,
    price = price,
    kilograms = kilograms,
    quantity = quantity,
    dateTimeUtc = Instant.parse(dateTimeUtc).atZone(ZoneId.of("UTC"))
)

fun Spending.toNewOrEditSpendingEntity(): SpendingEntity = SpendingEntity(
    // spendingId 0 və ya null olsa -> null (yeni xərc), əks halda öz ID-si (redaktə)
    spendingId = if (spendingId == 0) null else spendingId,
    name = name,
    price = price,
    kilograms = kilograms,
    quantity = quantity,
    dateTimeUtc = dateTimeUtc.toInstant().toString()
)
