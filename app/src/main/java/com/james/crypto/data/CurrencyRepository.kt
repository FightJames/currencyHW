package com.james.crypto.data

import com.james.crypto.data.source.model.Currency

interface CurrencyRepository {

    suspend fun insertCoinToDB()

    suspend fun getAllCryptoCurrency(): List<Currency>

    suspend fun getAllFiatCurrency(): List<Currency>

    suspend fun clearDB()
}