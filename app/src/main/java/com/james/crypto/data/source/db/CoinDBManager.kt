package com.james.crypto.data.source.db

import android.content.Context
import androidx.room.Room
import com.james.crypto.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class CoinDBManager @Inject constructor(
    @ApplicationContext applicationContext: Context,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    private val coinDB: CoinDB by lazy {
        Room.databaseBuilder(
            applicationContext,
            CoinDB::class.java, CoinDB.NAME
        ).build()
    }

    fun getCoinDAO() = coinDB.coinDao()

    suspend fun clearAllTable() = withContext(dispatcher) { coinDB.clearAllTables() }

    suspend fun closeDB() = withContext(dispatcher) {
        coinDB.close()
    }
}