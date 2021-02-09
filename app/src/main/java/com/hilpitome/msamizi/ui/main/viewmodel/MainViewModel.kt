package com.hilpitome.msamizi.ui.main.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hilpitome.msamizi.data.local.Inventory
import com.hilpitome.msamizi.data.local.InventoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application?) : AndroidViewModel(
    application!!
) {
    val inventoryList: LiveData<List<Inventory>>
    private val inventoryRepository: InventoryRepository

    init {
        inventoryRepository = InventoryRepository(application!!)
        inventoryList = inventoryRepository.fetchInventoryList()
    }
    fun insertInventory(name: String?, unitOfMeasure: String?) {
        val inventory = Inventory(null, name!!, unitOfMeasure!!)
        viewModelScope.launch(Dispatchers.IO) {
            inventoryRepository.insertInventory(inventory)
        }
    }


}