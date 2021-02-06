package com.hilpitome.msamizi.data.local



import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hilpitome.msamizi.data.UnitOfMeasure

@Entity(tableName = "inventory_items")
data class Inventory(
    @PrimaryKey
    val id:Int? = null,
    val name:String,
    val cost:Float,
    val uom:UnitOfMeasure,
    val supplier: Supplier
)