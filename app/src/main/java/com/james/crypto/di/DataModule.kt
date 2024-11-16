package com.james.crypto.di

import com.james.crypto.data.CurrencyRepository
import com.james.crypto.data.CurrencyRepositoryImpl
import com.james.crypto.data.source.assets.CoinAssetsSource
import com.james.crypto.data.source.assets.CoinAssetsSourceImpl
import com.james.crypto.data.source.db.CoinDBSource
import com.james.crypto.data.source.db.CoinDBSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    abstract fun bindCoinDBSource(
        coinDBSource: CoinDBSourceImpl
    ): CoinDBSource

    @Binds
    abstract fun bindCoinAssetsSource(
        coinAssetsSource: CoinAssetsSourceImpl
    ): CoinAssetsSource
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCurrencyRepository(
        repo: CurrencyRepositoryImpl
    ): CurrencyRepository

}
