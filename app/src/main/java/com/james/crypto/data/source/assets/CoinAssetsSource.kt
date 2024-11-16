package com.james.crypto.data.source.assets

interface CoinAssetsSource {

    suspend fun readCryptoCurrencyInfo(): List<CryptoCurrencyInfo>

    suspend fun readFiatCurrencyInfo(): List<FiatCurrencyInfo>
}