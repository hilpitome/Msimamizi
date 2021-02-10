package com.hilpitome.msamizi.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hilpitome.msamizi.data.Converters

@Database(
    entities = [Inventory::class, Supplier::class],
    version = 1,
    exportSchema = false
)
@TypeConverters( Converters::class)
abstract class MsimamiziDatabase: RoomDatabase() {
    abstract fun inventoryDao():InventoryDao
    abstract fun supplierDao():SupplierDao
    abstract fun stockDao():StockDao
}