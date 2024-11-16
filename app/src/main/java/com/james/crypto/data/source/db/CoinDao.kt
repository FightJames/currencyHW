package com.james.crypto.data.source.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CoinDao {

    @Query("SELECT * FROM ${CoinDB.CRYPTO_TABLE_NAME}")
    suspend fun getAllCryptoCurrency(): List<CryptoCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrypto(cryptoCurrency: CryptoCurrency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrypto(cryptoCurrency: List<CryptoCurrency>)


//    @Delete
//    suspend fun deleteTimerData(timerData: TimerData)

}