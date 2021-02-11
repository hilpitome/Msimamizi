package com.hilpitome.msamizi.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.hilpitome.msamizi.MsimamiziApplication

class StockRepository (application: Application){
    var database:MsimamiziDatabase
    val msimamiziApplication = application as MsimamiziApplication
    init {
        database = msimamiziApplication.msimamiziDb
    }
    fun fetchStockListByInventoryId(invId:Int): LiveData<List<Stock>> {
        return database.stockDao().observeAllStocksByInvId(invId)
    }

    suspend fun insertStock(stock: Stock){
        database.stockDao().insertStock(stock)
    }
//    fun fetchStockBy(id:Int): LiveData<Inventory> {
//        return database.stockDao().
//    }
}