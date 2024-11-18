package com.james.crypto.data

import com.james.crypto.data.search.CurrencyTrie
import com.james.crypto.data.source.assets.CoinAssetsSource
import com.james.crypto.data.source.assets.CryptoCurrencyInfo
import com.james.crypto.data.source.assets.FiatCurrencyInfo
import com.james.crypto.data.source.db.CoinDBSource
import com.james.crypto.data.source.db.CryptoCurrency
import com.james.crypto.data.source.db.FiatCurrency
import com.james.crypto.di.IoDispatcher
import com.james.crypto.data.source.model.Currency
import com.james.crypto.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRepositoryImpl @Inject constructor(
    private val coinDBSource: CoinDBSource,
    private val coinAssetsSource: CoinAssetsSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : CurrencyRepository {
    private val cryptoCurrencySearchTree = CurrencyTrie()
    private val fiatCurrencySearchTree = CurrencyTrie()
    private val mutex = Mutex()

    override suspend fun insertCoinToDB() = withContext(ioDispatcher) {
        mutex.withLock {
            val fiatCurrencyList = coinAssetsSource.readFiatCurrencyInfo().map { it.toFiatCurrency() }
            val cryptoCurrencyList = coinAssetsSource.readCryptoCurrencyInfo().map { it.toCryptoCurrency() }
            coinDBSource.insertFiatCurrency(fiatCurrencyList)
            coinDBSource.insertCryptoCurrency(cryptoCurrencyList)
            cryptoCurrencySearchTree.reset()
            fiatCurrencySearchTree.reset()
            cryptoCurrencyList.forEach {
                cryptoCurrencySearchTree.insert(it.toCurrency())
            }
            fiatCurrencyList.forEach {
                fiatCurrencySearchTree.insert(it.toCurrency())
            }
        }
    }

    override suspend fun getAllCryptoCurrency() = withContext(ioDispatcher) {
        coinDBSource.getAllCryptoCurrency().map { it.toCurrency() }
    }

    override suspend fun getAllFiatCurrency() = withContext(ioDispatcher) {
        coinDBSource.getAllFiatCurrency().map { it.toCurrency() }
    }

    override suspend fun searchCrypto(keyword: String): List<Currency> = withContext(defaultDispatcher) {
        if (cryptoCurrencySearchTree.isEmpty()) {
            mutex.withLock {
                if (cryptoCurrencySearchTree.isEmpty()) {
                    getAllCryptoCurrency().forEach {
                        cryptoCurrencySearchTree.insert(it)
                    }
                }
            }
        }
        return@withContext cryptoCurrencySearchTree.search(keyword)
    }

    override suspend fun searchFiat(keyword: String): List<Currency> = withContext(defaultDispatcher) {
        if (fiatCurrencySearchTree.isEmpty()) {
            mutex.withLock {
                if (fiatCurrencySearchTree.isEmpty()) {
                    getAllFiatCurrency().forEach {
                        fiatCurrencySearchTree.insert(it)
                    }
                }
            }
        }
        return@withContext fiatCurrencySearchTree.search(keyword)
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