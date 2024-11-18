package com.james.crypto.usecase

import com.james.crypto.CurrencyFakeDataHelper
import com.james.crypto.data.CurrencyRepository
import com.james.crypto.di.DefaultDispatcher
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetAllCurrencyUseCaseUnitTest {

    lateinit var currencyRepository: CurrencyRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    lateinit var getAllCurrencyUseCase: GetAllCurrencyUseCase

    @Before
    fun setup() {
        currencyRepository = mockk()
        getAllCurrencyUseCase = GetAllCurrencyUseCase(currencyRepository, testDispatcher)
    }

    @Test
    fun testGetAllCurrencyUseCase() {
        runTest(testDispatcher) {
            val listC = CurrencyFakeDataHelper.cryptoCurrencyFakeData()
            val listF = CurrencyFakeDataHelper.fiatCurrencyFakeData()
            coEvery { currencyRepository.getAllFiatCurrency() } returns listF
            coEvery { currencyRepository.getAllCryptoCurrency() } returns listC
            val actual = getAllCurrencyUseCase()
            Assert.assertEquals(listF + listC, actual)
        }
    }

    @After
    fun clear() {
        unmockkAll()
    }
}