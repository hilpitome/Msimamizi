package com.hilpitome.msamizi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "stock_table")
data class Stock(
    @PrimaryKey
    val id:Int?=null,
    val inventory_id:Int?=null,
    var qty:Int,
    val cost:Float,
    val dateCreated: Date,
    var dateUpdated: Date
)
