package com.james.crypto.usecase

import com.james.crypto.CurrencyFakeDataHelper
import com.james.crypto.data.CurrencyRepository
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchAllCurrencyUseCaseUnitTest {

    lateinit var currencyRepository: CurrencyRepository
    lateinit var searchAllCurrencyUseCase: SearchAllCurrencyUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        currencyRepository = mockk()
        searchAllCurrencyUseCase = SearchAllCurrencyUseCase(currencyRepository)
    }

    @Test
    fun testSearchAllCurrencyUseCase() {
        runTest(testDispatcher) {
            val fiat = CurrencyFakeDataHelper.fiatCurrencyFakeData()
            val crypto = CurrencyFakeDataHelper.cryptoCurrencyFakeData()
            val expect = fiat + crypto
            coEvery { currencyRepository.searchFiat(any()) } returns fiat
            coEvery { currencyRepository.searchCrypto(any()) } returns crypto
            val actual = searchAllCurrencyUseCase("test")
            Assert.assertEquals(expect, actual)
        }
    }

    @After
    fun clear() {
        unmockkAll()
    }
}