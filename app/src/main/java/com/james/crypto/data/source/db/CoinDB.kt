package com.james.crypto.data.source.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CryptoCurrency::class, FiatCurrency::class], version = 1)
abstract class CoinDB : RoomDatabase() {
    abstract fun coinDao(): CoinDao

    companion object {
        const val NAME = "coin-database"
        const val CRYPTO_TABLE_NAME = "Crypto"
        const val FIAT_TABLE_NAME = "Fiat"
    }
}

