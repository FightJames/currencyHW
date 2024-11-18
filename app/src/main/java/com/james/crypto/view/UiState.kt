package com.james.crypto.view

import CurrencyCategory
import com.james.crypto.data.source.model.Currency

sealed class IndexUiState {
    data object Idle : IndexUiState()
    data class Success(val message: String) : IndexUiState()
    data class Error(val error: String) : IndexUiState()
}

sealed class CurrencyUiState {
    data object Idle : CurrencyUiState()
    data class LoadCurrencySuccess(val currencyList: List<CurrencyCategory>) : CurrencyUiState()
    data object LoadingEmpty : CurrencyUiState()
    data class LoadingError(val error: String) : CurrencyUiState()
    data object SearchEmpty : CurrencyUiState()
    data class SearchResult(val currencyList: List<Currency>) : CurrencyUiState()
    data class SearchError(val error: String) : CurrencyUiState()
}
