package com.james.crypto.data

import com.james.crypto.data.source.assets.CoinAssetsSource
import com.james.crypto.data.source.assets.CryptoCurrencyInfo
import com.james.crypto.data.source.assets.FiatCurrencyInfo
import com.james.crypto.data.source.db.CoinDBSource
import com.james.crypto.data.source.db.CryptoCurrency
import com.james.crypto.data.source.db.FiatCurrency
import com.james.crypto.di.IoDispatcher
import com.james.crypto.data.source.model.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl @Inject constructor(
    private val coinDBSource: CoinDBSource,
    private val coinAssetsSource: CoinAssetsSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): CurrencyRepository {

    override suspend fun insertCoinToDB() = withContext(dispatcher) {
        coinDBSource.insertFiatCurrency(
            coinAssetsSource.readFiatCurrencyInfo().map { it.toFiatCurrency() })
        coinDBSource.insertCryptoCurrency(
            coinAssetsSource.readCryptoCurrencyInfo().map { it.toCryptoCurrency() })
    }

    override suspend fun getAllCryptoCurrency() = withContext(dispatcher) {
        coinDBSource.getAllCryptoCurrency().map { it.toCurrency() }
    }

    override suspend fun getAllFiatCurrency() = withContext(dispatcher) {
        coinDBSource.getAllFiatCurrency().map { it.toCurrency() }
    }

    override suspend fun clearDB() {
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

    private fun CryptoCurrency.toCurrency() =
        Currency(
            name = this.name,
            code = this.symbol
        )

    private fun FiatCurrency.toCurrency() =
        Currency(
            name = this.name,
            code = this.code
        )
}