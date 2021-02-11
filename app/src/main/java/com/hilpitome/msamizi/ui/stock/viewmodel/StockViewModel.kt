package com.hilpitome.msamizi.ui.stock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hilpitome.msamizi.data.local.Inventory
import com.hilpitome.msamizi.data.local.InventoryRepository
import com.hilpitome.msamizi.data.local.Stock
import com.hilpitome.msamizi.data.local.StockRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StockViewModel (application: Application?): AndroidViewModel(application!!) {
   // lateinit var inventory: LiveData<Inventory>
    private val stockRepository: StockRepository


    init {
        stockRepository = StockRepository(application!!)
    }
    fun fetchStockByInventoryId(inventoryId:Int): LiveData<List<Stock>> {

//        viewModelScope.launch(Dispatchers.IO) {
        return stockRepository.fetchStockListByInventoryId(inventoryId)

//        }
    }

    fun insertStock(stock: Stock) {
        viewModelScope.launch(Dispatchers.IO) {
            stockRepository.insertStock(stock)
        }

    }

}