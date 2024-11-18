package com.james.crypto.usecase

import com.james.crypto.CurrencyFakeDataHelper
import com.james.crypto.data.CurrencyRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class InsertDataUseCaseUnitTest {

    lateinit var currencyRepository: CurrencyRepository
    lateinit var insertDataUseCase: InsertDataUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        currencyRepository = mockk()
        insertDataUseCase = InsertDataUseCase(currencyRepository)
    }

    @Test
    fun testInsertCoinToDBUseCase() {
        runTest(testDispatcher) {
            coEvery { currencyRepository.insertCoinToDB() } just Runs
            insertDataUseCase()
            coVerify(exactly = 1) {  currencyRepository.insertCoinToDB() }
        }
    }

    @After
    fun clear() {
        unmockkAll()
    }
}