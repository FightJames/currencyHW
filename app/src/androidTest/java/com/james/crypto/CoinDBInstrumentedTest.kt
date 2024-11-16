package com.james.crypto

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.james.crypto.data.source.db.CoinDBManager
import com.james.crypto.data.source.db.CryptoCurrency
import com.james.crypto.data.source.db.FiatCurrency
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CoinDBInstrumentedTest {

    lateinit var coinDBManager: CoinDBManager
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        coinDBManager = CoinDBManager(context, testDispatcher)
    }

    @Test
    fun writeCryptoCurrencyAndReadInList() {
        runTest(testDispatcher) {
            val data = CryptoCurrency(id = "test", name = "test1", symbol = "test2")
            val dao = coinDBManager.getCoinDAO()
            dao.insertCrypto(data)
            Assert.assertEquals(data, dao.getAllCryptoCurrency()[0])
        }
    }

    @Test
    fun writeCryptoCurrencyListAndRead() {
        runTest(testDispatcher) {
            val cryptoCurrency = CryptoCurrency(id = "test", name = "test1", symbol = "test2")
            val data = listOf(cryptoCurrency, cryptoCurrency.copy(id = "test3"))
            val dao = coinDBManager.getCoinDAO()
            dao.insertCrypto(data)
            Assert.assertEquals(data, dao.getAllCryptoCurrency())
        }
    }

    @Test
    fun writeFiatCurrencyAndReadInList() {
        runTest(testDispatcher) {
            val data = FiatCurrency(id = "test", name = "test1", symbol = "test2", code = "code")
            val dao = coinDBManager.getCoinDAO()
            dao.insertFiat(data)
            Assert.assertEquals(data ,dao.getAllFiatCurrency()[0])
        }
    }

    @Test
    fun writeFiatCurrencyListAndRead() {
        runTest(testDispatcher) {
            val fiatCurrency = FiatCurrency(id = "test", name = "test1", symbol = "test2", code = "code")
            val data = listOf(fiatCurrency, fiatCurrency.copy(id = "test33"))
            val dao = coinDBManager.getCoinDAO()
            dao.insertFiat(data)
            Assert.assertEquals(data, dao.getAllFiatCurrency())
        }
    }

    @Test
    fun writeCryptoFiatCurrentAndClear() {
        runTest(testDispatcher) {
            val fiatCurrency = FiatCurrency(id = "test", name = "test1", symbol = "test2", code = "code")
            val cryptoCurrency = CryptoCurrency(id = "test", name = "test1", symbol = "test2")
            val dao = coinDBManager.getCoinDAO()
            dao.insertFiat(fiatCurrency)
            dao.insertCrypto(cryptoCurrency)
            Assert.assertTrue(dao.getAllCryptoCurrency().isNotEmpty())
            Assert.assertTrue(dao.getAllFiatCurrency().isNotEmpty())
            coinDBManager.clearAllTable()
            Assert.assertTrue(dao.getAllCryptoCurrency().isEmpty())
            Assert.assertTrue(dao.getAllFiatCurrency().isEmpty())
        }
    }

    @After
    fun clear() {
        runTest(testDispatcher) {
            coinDBManager.clearAllTable()
            coinDBManager.closeDB()
        }
    }
}