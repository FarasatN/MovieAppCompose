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

fun Spending.toNewSpendingEntity(): SpendingEntity = SpendingEntity(
    name = name,
    price = price,
    kilograms = kilograms,
    quantity = quantity,
    dateTimeUtc = dateTimeUtc.toInstant().toString()
)

fun Spending.toEditSpendingEntity(): SpendingEntity = SpendingEntity(
    spendingId = spendingId,
    name = name,
    price = price,
    kilograms = kilograms,
    quantity = quantity,
    dateTimeUtc = dateTimeUtc.toInstant().toString()
)

fun Spending.toNewOrEditSpendingEntity(): SpendingEntity = SpendingEntity(
    // Əgər spendingId 0-dırsa null keçsin (yeni xərc üçün), 0-dan böyükdürsə öz ID-si keçsin (edit üçün)
    spendingId = if (spendingId == 0) null else spendingId,
    name = name,
    price = price,
    kilograms = kilograms,
    quantity = quantity,
    dateTimeUtc = dateTimeUtc.toInstant().toString()
)