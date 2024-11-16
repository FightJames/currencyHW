package com.james.crypto.data.source.assets

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.james.crypto.di.IoDispatcher
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinAssetsSourceImpl @Inject constructor(
    @ApplicationContext val context: Context,
    @IoDispatcher val dispatcher: CoroutineDispatcher
) : CoinAssetsSource {
    private val gson: Gson by lazy {
        GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()
    }

    override suspend fun readCryptoCurrencyInfo(): List<CryptoCurrencyInfo> = withContext(dispatcher) {
        val str = context.assets.open("CryptoCurrency.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<CryptoCurrencyInfo>>() {}.type
        gson.fromJson(str, listType)
    }

    override suspend fun readFiatCurrencyInfo(): List<FiatCurrencyInfo> = withContext(dispatcher) {
        val str = context.assets.open("FiatCurrency.json").bufferedReader().use { it.readText() }
        val listType = object : TypeToken<List<FiatCurrencyInfo>>() {}.type
        gson.fromJson(str, listType)
    }
}