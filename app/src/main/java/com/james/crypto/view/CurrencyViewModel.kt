package com.james.crypto.view

import CurrencyCategory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.james.crypto.data.source.model.Currency
import com.james.crypto.usecase.GetAllCurrencyUseCase
import com.james.crypto.usecase.GetCryptoUseCase
import com.james.crypto.usecase.GetFiatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val getCryptoUseCase: GetCryptoUseCase,
    private val getAllCurrencyUseCase: GetAllCurrencyUseCase,
    private val getFiatUseCase: GetFiatUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<CurrencyUiState>(CurrencyUiState.Idle)
    val uiState: StateFlow<CurrencyUiState> get() = _uiState

    fun getCrypto() {
        viewModelScope.launch {
            runCatching {
                getCryptoUseCase()
            }.onSuccess(::handleCurrencyList)
            .onFailure(::handleError)
        }
    }

    fun getFiat() {
        viewModelScope.launch {
            runCatching {
                getFiatUseCase()
            }.onSuccess(::handleCurrencyList)
                .onFailure(::handleError)
        }
    }

    fun getAllCurrency() {
        viewModelScope.launch {
            runCatching {
                getAllCurrencyUseCase()
            }.onSuccess(::handleCurrencyList)
                .onFailure(::handleError)
        }

    }

    private fun handleError(t: Throwable) {
        _uiState.update {
            CurrencyUiState.Error(t.message ?: "unknown error")
        }
    }

    private fun handleCurrencyList(currencyList: List<Currency>) {
        if (currencyList.isEmpty()) {
            _uiState.update {
                CurrencyUiState.Empty
            }
            return
        }
        val res = currencyList.groupBy { it.name }.map { entry ->
            CurrencyCategory(entry.key, entry.value)
        }
        _uiState.update {
            CurrencyUiState.LoadCurrencySuccess(res)
        }
    }
}