package com.farasatnovruzov.spendingtracker.core.data

import com.farasatnovruzov.spendingtracker.core.data.local.SpendingDao
import com.farasatnovruzov.spendingtracker.core.domain.LocalSpendingDataSource
import com.farasatnovruzov.spendingtracker.core.domain.Spending
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomSpendingDataSource(
    private val dao: SpendingDao
) : LocalSpendingDataSource {

    override fun observeAllSpendings(): Flow<List<Spending>> {
        return dao.observeAllSpendings().map { entities ->
            entities.map { it.toSpending() }
        }
    }

    override fun observeTotalSpent(): Flow<Double> {
        return dao.observeTotalSpent().map { it ?: 0.0 }
    }

    override suspend fun getSpending(id: Int): Spending? {
        return dao.getSpending(id)?.toSpending()
    }

    override suspend fun upsertSpending(spending: Spending) {
        dao.upsertSpending(spending.toNewOrEditSpendingEntity())
    }

    override suspend fun deleteSpending(id: Int) {
        dao.deleteSpending(id)
    }
}
