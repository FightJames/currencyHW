package com.james.crypto.view

import CurrencyCategory

sealed class IndexUiState {
    data object Idle : IndexUiState()
    data class Success(val message: String) : IndexUiState()
    data class Error(val error: String) : IndexUiState()
}

sealed class CurrencyUiState {
    data object Idle : CurrencyUiState()
    data class LoadCurrencySuccess(val currencyList: List<CurrencyCategory>) : CurrencyUiState()
    data object Empty : CurrencyUiState()
    data class Error(val error: String) : CurrencyUiState()
}
