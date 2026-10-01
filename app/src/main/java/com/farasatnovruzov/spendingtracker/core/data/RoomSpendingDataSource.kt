package com.farasatnovruzov.spendingtracker.core.data

import com.farasatnovruzov.spendingtracker.core.data.local.SpendingDao
import com.farasatnovruzov.spendingtracker.core.domain.LocalSpendingDataSource
import com.farasatnovruzov.spendingtracker.core.domain.Spending
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

class RoomSpendingDataSource(
    private val dao: SpendingDao
) : LocalSpendingDataSource {
    override suspend fun getAllSpendings(): List<Spending> {
        return dao.getAllSpendings().map { it.toSpending() }
    }

    override suspend fun getAllSpendingsByDate(dateTimeUtc: ZonedDateTime): List<Spending> {
        val dateIso = dateTimeUtc.toLocalDate().toString()
        return dao.getSpendingsByDate(dateIso).map { it.toSpending() }
    }

    override suspend fun getAllDates(): List<ZonedDateTime> {
        return dao.getAllUniqueDates().map { dateStr ->
            LocalDate.parse(dateStr).atStartOfDay(ZoneId.of("UTC"))
        }
    }

    override suspend fun getSpending(id: Int): Spending? {
        return dao.getSpending(id)?.toSpending()
    }

    override suspend fun upsertSpending(spending: Spending) {
        dao.upsertSpending(spending.toNewOrEditSpendingEntity())
    }

    override suspend fun getSpendBalance(): Double {
        return dao.getSpendBalance() ?: 0.0
    }

    override suspend fun deleteSpending(id: Int) {
        dao.deleteSpending(id)
    }
}