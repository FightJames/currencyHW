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
    suspend fun insertCrypto(cryptoCurrencyList: List<CryptoCurrency>)

    @Query("SELECT * FROM ${CoinDB.FIAT_TABLE_NAME}")
    suspend fun getAllFiatCurrency(): List<FiatCurrency>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiat(fiatCurrency: FiatCurrency)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFiat(fiatCurrencyList: List<FiatCurrency>)
}