package com.hilpitome.msamizi.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.hilpitome.msamizi.getOrAwaitValueTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class StockDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: MsimamiziDatabase
    private lateinit var stockDao: StockDao


    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MsimamiziDatabase::class.java
        ).allowMainThreadQueries().build()
        stockDao = database.stockDao()
    }

    @After
    fun tearDown(){
        database.close()
    }


    @Test
    // use run blocking because we do not want concurrency
    fun insertStock() = runBlockingTest{
        val stock = Stock(1, 2,3,
            176f, Date(System.currentTimeMillis()), Date(System.currentTimeMillis()))
        stockDao.insertStock(stock)
        val allStock = stockDao.observeAllStocks().getOrAwaitValueTest()
        assertThat(allStock).contains(stock)
    }

    @Test
    fun deleteSupplier() = runBlockingTest {
        val stock = Stock(8, 2,4,
            179f, Date(System.currentTimeMillis()), Date(System.currentTimeMillis()))
        stockDao.insertStock(stock)
        stockDao.deleteStock(stock)
        val allStock = stockDao.observeAllStocks().getOrAwaitValueTest()
        assertThat(allStock).doesNotContain(stock)
    }


}