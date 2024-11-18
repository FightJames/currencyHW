package com.james.crypto.usecase

import com.james.crypto.data.CurrencyRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class ClearDataUseCaseUnitTest {

    lateinit var currencyRepository: CurrencyRepository
    lateinit var clearDataUseCase: ClearDataUseCase
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        currencyRepository = mockk()
        clearDataUseCase = ClearDataUseCase(currencyRepository)
    }

    @Test
    fun testClearData() {
        runTest(testDispatcher) {
            coEvery { currencyRepository.clearDB() } just Runs
            clearDataUseCase()
            coVerify(exactly = 1) { currencyRepository.clearDB() }
        }
    }

    @After
    fun clear() {
        unmockkAll()
    }
}