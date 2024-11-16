package com.james.crypto.data

import com.james.crypto.data.source.assets.CoinAssetsSource
import com.james.crypto.data.source.assets.CryptoCurrencyInfo
import com.james.crypto.data.source.assets.FiatCurrencyInfo
import com.james.crypto.data.source.db.CoinDBSource
import com.james.crypto.data.source.db.CryptoCurrency
import com.james.crypto.data.source.db.FiatCurrency
import com.james.crypto.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val coinDBSource: CoinDBSource,
    private val coinAssetsSource: CoinAssetsSource,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) {

    suspend fun insertCoinToDB() = withContext(dispatcher) {
        coinDBSource.insertFiatCurrency(
            coinAssetsSource.readFiatCurrencyInfo().map { it.toFiatCurrency() })
        coinDBSource.insertCryptoCurrency(
            coinAssetsSource.readCryptoCurrencyInfo().map { it.toCryptoCurrency() })
    }

    suspend fun getAllCryptoCurrency() = withContext(dispatcher) {
        coinDBSource.getAllCryptoCurrency()
    }

    suspend fun getAllFiatCurrency() = withContext(dispatcher) {
        coinDBSource.getAllFiatCurrency()
    }

    suspend fun clearDB() {
        coinDBSource.clearDB()
    }

   private fun CryptoCurrencyInfo.toCryptoCurrency() =
        CryptoCurrency(
            id = this.id,
            name = this.name,
            symbol = this.symbol
        )

    private fun FiatCurrencyInfo.toFiatCurrency() =
        FiatCurrency(
            id = this.id,
            name = this.name,
            symbol = this.symbol,
            code = this.code
        )

}