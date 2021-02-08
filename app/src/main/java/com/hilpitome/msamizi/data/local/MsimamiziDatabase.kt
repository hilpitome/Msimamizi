package com.hilpitome.msamizi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Inventory::class, Supplier::class],
    version = 1,
    exportSchema = false
)
abstract class MsimamiziDatabase: RoomDatabase() {
    abstract fun inventoryDao():InventoryDao
    abstract fun supplierDao():SupplierDao
}