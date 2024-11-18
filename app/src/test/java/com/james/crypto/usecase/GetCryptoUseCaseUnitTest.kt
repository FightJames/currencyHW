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

class GetCryptoUseCaseUnitTest {

    lateinit var currencyRepository: CurrencyRepository
    lateinit var getCryptoUseCase: GetCryptoUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        currencyRepository = mockk()
        getCryptoUseCase = GetCryptoUseCase(currencyRepository)
    }

    @Test
    fun testGetCryptoUseCase() {
        runTest(testDispatcher) {
            val expected = CurrencyFakeDataHelper.cryptoCurrencyFakeData()
            coEvery { currencyRepository.getAllCryptoCurrency() } returns expected
            val actual = getCryptoUseCase()
            Assert.assertEquals(expected, actual)
        }
    }

    @After
    fun clear() {
        unmockkAll()
    }
}