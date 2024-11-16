package com.james.crypto.data.source.db

import javax.inject.Inject

class CoinDBSourceImpl @Inject constructor(
    private val coinDBManager: CoinDBManager
) : CoinDBSource {

    private val coinDao: CoinDao by lazy {
        coinDBManager.getCoinDAO()
    }

    override suspend fun getAllCryptoCurrency(): List<CryptoCurrency> {
        return coinDao.getAllCryptoCurrency()
    }

    override suspend fun getAllFiatCurrency(): List<FiatCurrency> {
        return coinDao.getAllFiatCurrency()
    }

    override suspend fun insertCryptoCurrency(cryptoCurrency: CryptoCurrency) {
        coinDao.insertCrypto(cryptoCurrency)
    }

    override suspend fun insertCryptoCurrency(cryptoCurrency: List<CryptoCurrency>) {
        coinDao.insertCrypto(cryptoCurrency)
    }

    override suspend fun insertFiatCurrency(cryptoCurrency: FiatCurrency) {
        coinDao.insertFiat(cryptoCurrency)
    }

    override suspend fun insertFiatCurrency(cryptoCurrency: List<FiatCurrency>) {
        coinDao.insertFiat(cryptoCurrency)
    }

    override suspend fun clearDB() {
        coinDBManager.clearAllTable()
    }
}