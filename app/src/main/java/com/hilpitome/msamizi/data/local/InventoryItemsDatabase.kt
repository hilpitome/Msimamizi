package com.hilpitome.msamizi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Supplier::class, Supplier::class],
    version = 1
)
abstract class InventoryItemsDatabase: RoomDatabase() {
    abstract fun inventoryDao():InventoryDao
    abstract fun supplierDao():SupplierDao
}