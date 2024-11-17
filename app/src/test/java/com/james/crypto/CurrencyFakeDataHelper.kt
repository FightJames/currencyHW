package com.james.crypto

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.james.crypto.data.source.assets.CryptoCurrencyInfo
import com.james.crypto.data.source.assets.FiatCurrencyInfo
import com.james.crypto.data.source.db.CryptoCurrency
import com.james.crypto.data.source.db.FiatCurrency
import com.james.crypto.data.source.model.Currency
import java.io.File

object CurrencyFakeDataHelper {

    fun cryptoCurrencyFakeData(): List<Currency> {
        val cryptoCurrencyJson = File("src/test/resources/CryptoCurrency.json").readText()
        val gson = Gson()
        val listType = object : TypeToken<List<CryptoCurrencyInfo>>() {}.type
        val cryptoCurrencyList: List<CryptoCurrencyInfo> =
            gson.fromJson(cryptoCurrencyJson, listType)
        return cryptoCurrencyList.map { it.toCurrency() }
    }

    fun fiatCurrencyFakeData(): List<Currency> {
        val fiatCurrencyJson = File("src/test/resources/FiatCurrency.json").readText()
        val gson = Gson()
        val listType = object : TypeToken<List<FiatCurrencyInfo>>() {}.type
        val fiatCurrencyList: List<FiatCurrencyInfo> = gson.fromJson(fiatCurrencyJson, listType)
        return fiatCurrencyList.map { it.toCurrency() }
    }

    fun cryptoCurrencyInfoFakeData(): List<CryptoCurrencyInfo> {
        val cryptoCurrencyJson = File("src/test/resources/CryptoCurrency.json").readText()
        val gson = Gson()
        val listType = object : TypeToken<List<CryptoCurrencyInfo>>() {}.type
        val cryptoCurrencyList: List<CryptoCurrencyInfo> =
            gson.fromJson(cryptoCurrencyJson, listType)
        return cryptoCurrencyList
    }

    fun fiatCurrencyInfoFakeData(): List<FiatCurrencyInfo> {
        val fiatCurrencyJson = File("src/test/resources/FiatCurrency.json").readText()
        val gson = Gson()
        val listType = object : TypeToken<List<FiatCurrencyInfo>>() {}.type
        val fiatCurrencyList: List<FiatCurrencyInfo> = gson.fromJson(fiatCurrencyJson, listType)
        return fiatCurrencyList
    }

    fun cryptoDBCurrencyFakeData(): List<CryptoCurrency> {
        val cryptoCurrencyJson = File("src/test/resources/CryptoCurrency.json").readText()
        val gson = Gson()
        val listType = object : TypeToken<List<CryptoCurrencyInfo>>() {}.type
        val cryptoCurrencyList: List<CryptoCurrencyInfo> =
            gson.fromJson(cryptoCurrencyJson, listType)
        return cryptoCurrencyList.map { it.toCryptoCurrency() }
    }

    fun fiatDBCurrencyFakeData(): List<FiatCurrency> {
        val fiatCurrencyJson = File("src/test/resources/FiatCurrency.json").readText()
        val gson = Gson()
        val listType = object : TypeToken<List<FiatCurrencyInfo>>() {}.type
        val fiatCurrencyList: List<FiatCurrencyInfo> = gson.fromJson(fiatCurrencyJson, listType)
        return fiatCurrencyList.map { it.toFiatCurrency() }
    }
}

fun CryptoCurrencyInfo.toCryptoCurrency() =
    CryptoCurrency(
        id = this.id,
        name = this.name,
        symbol = this.symbol
    )

fun FiatCurrencyInfo.toFiatCurrency() =
    FiatCurrency(
        id = this.id,
        name = this.name,
        symbol = this.symbol,
        code = this.code
    )

fun CryptoCurrencyInfo.toCurrency() =
    Currency(
        name = this.name,
        code = this.symbol
    )

fun FiatCurrencyInfo.toCurrency() =
    Currency(
        name = this.name,
        code = this.code
    )
