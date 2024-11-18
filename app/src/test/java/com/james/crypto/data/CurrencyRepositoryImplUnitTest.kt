package com.james.crypto.data

import com.james.crypto.CurrencyFakeDataHelper
import com.james.crypto.data.search.CurrencyTrie
import com.james.crypto.data.source.assets.CoinAssetsSource
import com.james.crypto.data.source.db.CoinDBSource
import com.james.crypto.data.source.db.CryptoCurrency
import com.james.crypto.data.source.db.FiatCurrency
import com.james.crypto.data.source.model.Currency
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CurrencyRepositoryImplUnitTest {

    lateinit var coinDBSource: CoinDBSource
    lateinit var coinAssetsSource: CoinAssetsSource
    lateinit var currencyRepositoryImpl: CurrencyRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        coinDBSource = mockk()
        coinAssetsSource = mockk()
        currencyRepositoryImpl =
            CurrencyRepositoryImpl(coinDBSource, coinAssetsSource, testDispatcher, testDispatcher)
    }

    @Test
    fun testInsertData() {
        runTest(testDispatcher) {
            // need to re-mock, because of CurrencyTrie
            mockkConstructor(CurrencyTrie::class)
            every { anyConstructed<CurrencyTrie>().reset() } just Runs
            every { anyConstructed<CurrencyTrie>().insert(any()) } just Runs
            currencyRepositoryImpl =
                CurrencyRepositoryImpl(
                    coinDBSource,
                    coinAssetsSource,
                    testDispatcher,
                    testDispatcher
                )
            coEvery { coinAssetsSource.readFiatCurrencyInfo() } returns CurrencyFakeDataHelper.fiatCurrencyInfoFakeData()
            coEvery { coinAssetsSource.readCryptoCurrencyInfo() } returns CurrencyFakeDataHelper.cryptoCurrencyInfoFakeData()
            coEvery { coinDBSource.insertFiatCurrency(any<List<FiatCurrency>>()) } just Runs
            coEvery { coinDBSource.insertCryptoCurrency(any<List<CryptoCurrency>>()) } just Runs
            currencyRepositoryImpl.insertCoinToDB()
            verify { anyConstructed<CurrencyTrie>().reset() }
            verify { anyConstructed<CurrencyTrie>().insert(any()) }
            coVerify(exactly = 1) { coinAssetsSource.readFiatCurrencyInfo() }
            coVerify(exactly = 1) { coinAssetsSource.readCryptoCurrencyInfo() }
            coVerify(exactly = 1) { coinDBSource.insertFiatCurrency(any<List<FiatCurrency>>()) }
            coVerify(exactly = 1) { coinDBSource.insertCryptoCurrency(any<List<CryptoCurrency>>()) }
        }
    }

    @Test
    fun testGetAllCryptoCurrency() {
        runTest(testDispatcher) {
            coEvery { coinDBSource.getAllCryptoCurrency() } returns CurrencyFakeDataHelper.cryptoDBCurrencyFakeData()
            val expected = CurrencyFakeDataHelper.cryptoCurrencyFakeData()
            val actual = currencyRepositoryImpl.getAllCryptoCurrency()
            coVerify(exactly = 1) { coinDBSource.getAllCryptoCurrency() }
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun testGetAllFiatCurrency() {
        runTest(testDispatcher) {
            coEvery { coinDBSource.getAllFiatCurrency() } returns CurrencyFakeDataHelper.fiatDBCurrencyFakeData()
            val expected = CurrencyFakeDataHelper.fiatCurrencyFakeData()
            val actual = currencyRepositoryImpl.getAllFiatCurrency()
            coVerify(exactly = 1) { coinDBSource.getAllFiatCurrency() }
            Assert.assertEquals(expected, actual)
        }
    }

    @Test
    fun testSearchCrypto() {
        val expect = listOf(Currency(name = "a", code = "b"))
        mockkConstructor(CurrencyTrie::class)
        every { anyConstructed<CurrencyTrie>().search(any()) } returns expect
        currencyRepositoryImpl =
            CurrencyRepositoryImpl(coinDBSource, coinAssetsSource, testDispatcher, testDispatcher)
        runTest(testDispatcher) {
            val actual = currencyRepositoryImpl.searchCrypto("b")
            verify { anyConstructed<CurrencyTrie>().search(any()) }
            Assert.assertEquals(expect, actual)
        }
    }

    @Test
    fun testSearchFiat() {
        val expect = listOf(Currency(name = "a", code = "b"))
        mockkConstructor(CurrencyTrie::class)
        every { anyConstructed<CurrencyTrie>().search(any()) } returns expect
        currencyRepositoryImpl =
            CurrencyRepositoryImpl(coinDBSource, coinAssetsSource, testDispatcher, testDispatcher)
        runTest(testDispatcher) {
            val actual = currencyRepositoryImpl.searchFiat("b")
            verify { anyConstructed<CurrencyTrie>().search(any()) }
            Assert.assertEquals(expect, actual)
        }
    }

    @Test
    fun testClearDB() {
        runTest(testDispatcher) {
            coEvery { coinDBSource.clearDB() } just Runs
            currencyRepositoryImpl.clearDB()
            coVerify(exactly = 1) { coinDBSource.clearDB() }
        }
    }

    @After
    fun clear() {
        clearAllMocks()
    }
}