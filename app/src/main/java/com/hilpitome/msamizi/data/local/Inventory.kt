package com.hilpitome.msamizi.data.local



import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_items")
data class Inventory(
    @PrimaryKey
    val id:Int? = null,
    val name:String,
    val uom:String, // string name for unit of measure
)