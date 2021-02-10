package com.hilpitome.msamizi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "stock_tracker")
data class StockTracker (
    @PrimaryKey
    val id:Int?=null,
    val batch_id:Int?=null, // stock id
    var qty:Int,
    val dateCreated: Date,
    val dateUpdated: Date
    )