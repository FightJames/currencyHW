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

class GetFiatUseCaseUnitTest {

    lateinit var currencyRepository: CurrencyRepository
    lateinit var getFiatUseCase: GetFiatUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        currencyRepository = mockk()
        getFiatUseCase = GetFiatUseCase(currencyRepository)
    }

    @Test
    fun testGetCryptoUseCase() {
        runTest(testDispatcher) {
            val expected = CurrencyFakeDataHelper.fiatCurrencyFakeData()
            coEvery { currencyRepository.getAllFiatCurrency() } returns expected
            val actual = getFiatUseCase()
            Assert.assertEquals(expected, actual)
        }
    }

    @After
    fun clear() {
        unmockkAll()
    }
}