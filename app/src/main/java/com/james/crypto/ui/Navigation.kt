package com.james.crypto.ui

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyList(val currencyType: CurrencyType): java.io.Serializable

@Serializable
enum class CurrencyType{
    CRYPTO, FIAT, CRYPTO_FIAT
}
