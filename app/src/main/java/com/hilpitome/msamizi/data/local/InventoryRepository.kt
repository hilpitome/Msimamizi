package com.hilpitome.msamizi.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.google.android.material.transition.MaterialSharedAxis
import com.hilpitome.msamizi.MsimamiziApplication

class InventoryRepository (application: Application){
    lateinit var database:MsimamiziDatabase
    val msimamiziApplication = application as MsimamiziApplication
    init {
        database = msimamiziApplication.msimamiziDb
    }
    fun fetchInventoryList(): LiveData<List<Inventory>> {
        return database.inventoryDao().observeAllInventoryItems()
    }

    suspend fun insertInventory(inventory: Inventory){
        database.inventoryDao().insertInventoryItem(inventory)
    }
}