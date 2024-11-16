package com.james.crypto

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.james.crypto.data.source.db.CoinDBManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
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
    fun testGetDao() {
        val dao = coinDBManager.getCoinDAO()
        println("dao $dao")
    }

    @After
    fun clear() {
        runTest(testDispatcher) {
            coinDBManager.closeDB()
        }
    }
}