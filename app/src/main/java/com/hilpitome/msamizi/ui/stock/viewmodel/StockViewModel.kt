package com.hilpitome.msamizi.ui.stock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.hilpitome.msamizi.data.local.Inventory
import com.hilpitome.msamizi.data.local.InventoryRepository

class StockViewModel (application: Application?): AndroidViewModel(application!!) {
   // lateinit var inventory: LiveData<Inventory>
    private val inventoryRepository: InventoryRepository


    init {
        inventoryRepository = InventoryRepository(application!!)
    }
    fun fetchInventoryById(id:Int): LiveData<Inventory> {

//        viewModelScope.launch(Dispatchers.IO) {
        return inventoryRepository.fetchInventoryById(id)

//        }
    }

}