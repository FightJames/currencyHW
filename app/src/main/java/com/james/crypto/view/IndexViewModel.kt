package com.james.crypto.view

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.james.crypto.usecase.ClearDataUseCase
import com.james.crypto.usecase.InsertDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IndexViewModel @Inject constructor(
    private val clearDataUseCase: ClearDataUseCase,
    private val insertDataUseCase: InsertDataUseCase,
) : ViewModel() {

    private val _uiState = MutableSharedFlow<IndexUiState>()
    val uiState: SharedFlow<IndexUiState>
        get() = _uiState

    fun clearData() {
        viewModelScope.launch {
            runCatching {
                clearDataUseCase()
            }.onSuccess {
                _uiState.emit(IndexUiState.Success("clear data success"))
            }.onFailure { t ->
                _uiState.emit(IndexUiState.Error(t.message ?: "unknown error"))
            }
        }
    }

    fun insertData() {
        viewModelScope.launch {
            kotlin.runCatching {
                insertDataUseCase()
            }.onSuccess {
                _uiState.emit(IndexUiState.Success("insert data success"))
            }.onFailure { t ->
                _uiState.emit(IndexUiState.Error(t.message ?: "unknown error"))
            }
        }
    }
}