package com.james.crypto

import com.james.crypto.data.source.db.CoinDBManager
import com.james.crypto.data.source.db.CoinDBSourceImpl
import com.james.crypto.data.source.db.CoinDao
import com.james.crypto.data.source.db.CryptoCurrency
import com.james.crypto.data.source.db.FiatCurrency
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CoinDBSourceImplUnitTest {
    lateinit var coinDBSourceImpl: CoinDBSourceImpl

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    lateinit var coinDBManager: CoinDBManager
    lateinit var dao: CoinDao

    @Before
    fun setup() {
        coinDBManager = mockk()
        dao = mockk()
        every { coinDBManager.getCoinDAO() } returns dao
        coinDBSourceImpl = CoinDBSourceImpl(coinDBManager)
    }

    @Test
    fun insertSingleCryptoCurrency() {
        runTest(testDispatcher) {
            val cryptoCurrency = CryptoCurrency(id = "test", name = "test1", symbol = "test2")
            val slot = slot<CryptoCurrency>()
            coEvery { dao.insertCrypto(capture(slot)) } just Runs
            coinDBSourceImpl.insertCryptoCurrency(cryptoCurrency)
            Assert.assertEquals(cryptoCurrency, slot.captured)
        }
    }

    @Test
    fun insertCryptoCurrencyList() {
        runTest(testDispatcher) {
            val cryptoCurrency = CryptoCurrency(id = "test", name = "test1", symbol = "test2")
            val data = listOf(cryptoCurrency, cryptoCurrency.copy(id = "test333"))
            val slot = slot<List<CryptoCurrency>>()
            coEvery { dao.insertCrypto(capture(slot)) } just Runs
            coinDBSourceImpl.insertCryptoCurrency(data)
            Assert.assertEquals(data, slot.captured)
        }
    }


    @Test
    fun insertSingleFiatCurrency() {
        runTest(testDispatcher) {
            val fiatCurrency = FiatCurrency(id = "test", name = "test1", symbol = "test2", code = "code1")
            val slot = slot<FiatCurrency>()
            coEvery { dao.insertFiat(capture(slot)) } just Runs
            coinDBSourceImpl.insertFiatCurrency(fiatCurrency)
            Assert.assertEquals(fiatCurrency, slot.captured)
        }
    }

    @Test
    fun insertFiatCurrencyList() {
        runTest(testDispatcher) {
            val fiatCurrency = FiatCurrency(id = "test", name = "test1", symbol = "test2", code = "code1")
            val data = listOf(fiatCurrency, fiatCurrency.copy(id = "test333", code = "code11"))
            val slot = slot<List<FiatCurrency>>()
            coEvery { dao.insertFiat(capture(slot)) } just Runs
            coinDBSourceImpl.insertFiatCurrency(data)
            Assert.assertEquals(data, slot.captured)
        }
    }

    @Test
    fun getAllCryptoCurrency() {
        runTest(testDispatcher) {
            val cryptoCurrency = CryptoCurrency(id = "test", name = "test1", symbol = "test2")
            val data = listOf(cryptoCurrency, cryptoCurrency.copy(id = "test333"))
            coEvery { dao.getAllCryptoCurrency() }  returns data
            val actual = coinDBSourceImpl.getAllCryptoCurrency()
            coVerify(exactly = 1) { dao.getAllCryptoCurrency() }
            Assert.assertEquals(data, actual)
        }
    }

    @Test
    fun getAllFiatCurrency() {
        runTest(testDispatcher) {
            val fiatCurrency = FiatCurrency(id = "test", name = "test1", symbol = "test2", code = "code1")
            val data = listOf(fiatCurrency, fiatCurrency.copy(id = "test333", code = "code11"))
            coEvery { dao.getAllFiatCurrency() }  returns data
            val actual = coinDBSourceImpl.getAllFiatCurrency()
            coVerify(exactly = 1) { dao.getAllFiatCurrency() }
            Assert.assertEquals(data, actual)
        }
    }

    @After
    fun clear() {
        clearAllMocks()
    }
}