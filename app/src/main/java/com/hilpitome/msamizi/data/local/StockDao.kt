package com.hilpitome.msamizi.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: Stock)

    @Delete
    suspend fun deleteStock(stock: Stock)

    @Query("SELECT * FROM stock_table")
    fun observeAllStocks(): LiveData<List<Stock>>

    @Query("SELECT * FROM stock_table where inventory_id= :invId and qty>0 order by id asc")
    fun observeAllStocksByInvId(invId:Int): LiveData<List<Stock>>
}
