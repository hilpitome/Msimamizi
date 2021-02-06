package com.hilpitome.msamizi.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SupplierDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSupplier(inventory: Supplier)

    @Delete
    suspend fun deleteSupplier(inventory: Supplier)

    @Query("SELECT * FROM suppliers")
    fun observeAllSuppliers(): LiveData<List<Supplier>>
}