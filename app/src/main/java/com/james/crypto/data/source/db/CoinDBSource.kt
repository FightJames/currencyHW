package com.james.crypto.data.source.db

interface CoinDBSource {

    suspend fun getAllCryptoCurrency(): List<CryptoCurrency>

    suspend fun getAllFiatCurrency(): List<FiatCurrency>

    suspend fun insertCryptoCurrency(cryptoCurrency: CryptoCurrency)

    suspend fun insertCryptoCurrency(cryptoCurrency: List<CryptoCurrency>)

    suspend fun insertFiatCurrency(cryptoCurrency: FiatCurrency)

    suspend fun insertFiatCurrency(cryptoCurrency: List<FiatCurrency>)

    suspend fun clearDB()
}