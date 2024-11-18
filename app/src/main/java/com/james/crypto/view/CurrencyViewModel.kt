package com.james.crypto.view

import CurrencyCategory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.james.crypto.data.source.model.Currency
import com.james.crypto.usecase.GetAllCurrencyUseCase
import com.james.crypto.usecase.GetCryptoUseCase
import com.james.crypto.usecase.GetFiatUseCase
import com.james.crypto.usecase.SearchAllCurrencyUseCase
import com.james.crypto.usecase.SearchCryptoUseCase
import com.james.crypto.usecase.SearchFiatUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Job
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
    private val searchAllCurrencyUseCase: SearchAllCurrencyUseCase,
    private val searchCryptoUseCase: SearchCryptoUseCase,
    private val searchFiatUseCase: SearchFiatUseCase
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

    private var searchJob: Job = CompletableDeferred(Unit)
    private var currentKeyword: String = ""

    fun searchCrypto(keyword: String) {
        if (keyword == currentKeyword) return
        if (searchJob.isActive) searchJob.cancel()
        currentKeyword = keyword
        searchJob = viewModelScope.launch {
            runCatching {
                if (keyword.isEmpty()) {
                    _uiState.update {
                        CurrencyUiState.SearchEmpty
                    }
                    return@launch
                }
                searchCryptoUseCase(keyword)
            }.onSuccess(::handleSearchResult)
                .onFailure(::handleSearchError)
        }
    }

    fun searchFiat(keyword: String) {
        if (keyword == currentKeyword) return
        if (searchJob.isActive) searchJob.cancel()
        currentKeyword = keyword
        searchJob = viewModelScope.launch {
            runCatching {
                if (keyword.isEmpty()) {
                    _uiState.update {
                        CurrencyUiState.SearchEmpty
                    }
                    return@launch
                }
                searchFiatUseCase(keyword)
            }.onSuccess(::handleSearchResult)
                .onFailure(::handleSearchError)
        }
    }

    fun searchAllCurrency(keyword: String) {
        if (keyword == currentKeyword) return
        if (searchJob.isActive) searchJob.cancel()
        currentKeyword = keyword
        searchJob = viewModelScope.launch {
            runCatching {
                if (keyword.isEmpty()) {
                    _uiState.update {
                        CurrencyUiState.SearchEmpty
                    }
                    return@launch
                }
                searchAllCurrencyUseCase(keyword)
            }.onSuccess(::handleSearchResult)
                .onFailure(::handleSearchError)
        }
    }

    fun cancelSearch() {
        currentKeyword = ""
        searchJob.cancel()
        _uiState.update {
            CurrencyUiState.LoadingEmpty
        }
    }

    fun clearSearch() {
        currentKeyword = ""
        searchJob.cancel()
        _uiState.update {
            CurrencyUiState.SearchEmpty
        }
    }

    fun isSearchState() =
        _uiState.value is CurrencyUiState.SearchError || _uiState.value is CurrencyUiState.SearchResult || _uiState.value is CurrencyUiState.SearchEmpty

    private fun handleError(t: Throwable) {
        _uiState.update {
            CurrencyUiState.LoadingError(t.message ?: "unknown error")
        }
    }

    private fun handleCurrencyList(currencyList: List<Currency>) {
        if (currencyList.isEmpty()) {
            _uiState.update {
                CurrencyUiState.LoadingEmpty
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

    private fun handleSearchResult(currencyList: List<Currency>) {
        if (currencyList.isEmpty()) {
            _uiState.update {
                CurrencyUiState.SearchEmpty
            }
            return
        }
        _uiState.update {
            CurrencyUiState.SearchResult(currencyList)
        }
    }

    private fun handleSearchError(t: Throwable) {
        _uiState.update {
            CurrencyUiState.SearchError(t.message ?: "unknown error")
        }
    }
}