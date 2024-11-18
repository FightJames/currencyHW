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

class SearchFiatUseCaseUnitTest {

    lateinit var currencyRepository: CurrencyRepository
    lateinit var searchFiatUseCase: SearchFiatUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        currencyRepository = mockk()
        searchFiatUseCase = SearchFiatUseCase(currencyRepository)
    }

    @Test
    fun testSearchCryptoUseCase() {
        runTest(testDispatcher) {
            val expect = CurrencyFakeDataHelper.fiatCurrencyFakeData()
            coEvery { currencyRepository.searchFiat(any()) } returns expect
            val actual = searchFiatUseCase("test")
            Assert.assertEquals(expect, actual)
        }
    }

    @After
    fun clear() {
        unmockkAll()
    }
}
