package com.james.crypto.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.james.crypto.usecase.ClearDataUseCase
import com.james.crypto.usecase.GetAllCurrencyUseCase
import com.james.crypto.usecase.GetFiatUseCase
import com.james.crypto.usecase.InsertDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val clearDataUseCase: ClearDataUseCase,
    private val insertDataUseCase: InsertDataUseCase,
    private val getAllCurrencyUseCase: GetAllCurrencyUseCase,
    private val getFiatUseCase: GetFiatUseCase,
) : ViewModel() {

    fun clearData () {
        viewModelScope.launch {
            clearDataUseCase()
        }
    }

    fun insertData() {
        viewModelScope.launch {
            insertDataUseCase()
        }
    }
}