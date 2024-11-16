package com.james.crypto.data.source.assets

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FiatCurrencyInfo(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("symbol")
    val symbol: String,
    @Expose
    @SerializedName("code")
    val code: String
)

data class CryptoCurrencyInfo(
    @Expose
    @SerializedName("id")
    val id: String,
    @Expose
    @SerializedName("name")
    val name: String,
    @Expose
    @SerializedName("symbol")
    val symbol: String,
)
