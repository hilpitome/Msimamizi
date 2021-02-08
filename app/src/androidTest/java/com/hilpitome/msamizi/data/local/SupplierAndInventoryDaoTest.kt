package com.hilpitome.msamizi.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.hilpitome.msamizi.getOrAwaitValueTest
import kotlinx.coroutines.test.runBlockingTest
import com.google.common.truth.Truth.assertThat
import com.hilpitome.msamizi.data.UnitOfMeasure
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest

class SupplierAndInventoryDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: MsimamiziDatabase
    private lateinit var inventoryDao:InventoryDao
    private lateinit var supplierDao: SupplierDao

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MsimamiziDatabase::class.java
        ).allowMainThreadQueries().build()
        inventoryDao = database.inventoryDao()
        supplierDao = database.supplierDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    @Test
    // use run blocking because we do not want concurrency
    fun insertSupplier() = runBlockingTest{
        val supplier = Supplier(1, "RoseWood")
        supplierDao.insertSupplier(supplier)
        val allSuppliers = supplierDao.observeAllSuppliers().getOrAwaitValueTest()
        assertThat(allSuppliers).contains(supplier)
    }
    
    @Test
    fun deleteSupplier() = runBlockingTest {
        val supplier = Supplier(1, "RoseWood")
        supplierDao.insertSupplier(supplier)
        supplierDao.deleteSupplier(supplier)
        val allSuppliers = supplierDao.observeAllSuppliers().getOrAwaitValueTest()
        assertThat(allSuppliers).doesNotContain(supplier)
    }

    @Test
    fun insertInventory() = runBlockingTest {
        val inventory = Inventory(1, "Blue Band",  "Kg", )
        inventoryDao.insertInventoryItem(inventory)
        val allInventory = inventoryDao.observeAllInventoryItems().getOrAwaitValueTest()
        assertThat(allInventory).contains(inventory)
    }



    @Test
    fun deleteInventoryItem() = runBlockingTest {
        val inventory = Inventory(1, "Blue Band",  "Kg")
        inventoryDao.insertInventoryItem(inventory)
        inventoryDao.deleteInventory(inventory)
        val allInventory = inventoryDao.observeAllInventoryItems().getOrAwaitValueTest()
        assertThat(allInventory).doesNotContain(inventory)
    }
}