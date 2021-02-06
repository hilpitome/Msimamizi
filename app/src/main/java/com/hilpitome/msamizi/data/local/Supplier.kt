package com.hilpitome.msamizi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "suppliers")
data class Supplier(
    @PrimaryKey
    val id:Int?=null,
    val name:String
    )
