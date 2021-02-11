package com.hilpitome.msamizi.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StockTrackerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStockTrackerRecord(stockTracker: StockTracker)

    @Delete
    suspend fun deleteStockTrackerRecord(stockTracker: StockTracker)

    @Query("SELECT * FROM stock_tracker")
    fun observeAllSuppliers(): LiveData<List<StockTracker>>
}