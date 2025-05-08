package com.currencyconverter.shared.viewmodel

import com.currencyconverter.shared.model.CurrencyConversion
import com.currencyconverter.shared.repository.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CurrencyViewModel : KoinComponent {
    private val repository: CurrencyRepository by inject()
    private val viewModelScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    private val _uiState = MutableStateFlow<CurrencyUiState>(CurrencyUiState.Initial)
    val uiState: StateFlow<CurrencyUiState> = _uiState.asStateFlow()
    
    private val _amount = MutableStateFlow("")
    val amount: StateFlow<String> = _amount.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    fun updateAmount(newAmount: String) {
        if (newAmount.isEmpty() || newAmount.matches(Regex("^\\d*\\.?\\d*$"))) {
            _amount.value = newAmount
            _error.value = null
        } else {
            _error.value = "Por favor, insira um valor numérico válido"
        }
    }
    
    fun convertToReal() {
        val amountValue = _amount.value.toDoubleOrNull()
        if (amountValue == null) {
            _error.value = "Por favor, insira um valor válido"
            return
        }
        
        if (amountValue <= 0) {
            _error.value = "O valor deve ser maior que zero"
            return
        }
        
        _uiState.value = CurrencyUiState.Loading
        _error.value = null
        
        viewModelScope.launch {
            try {
                val result = repository.convertCurrency(amountValue, "USD", "BRL")
                _uiState.value = CurrencyUiState.Success(result)
            } catch (e: CurrencyException) {
                _error.value = e.message
                _uiState.value = CurrencyUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
    
    fun convertToDollar() {
        val amountValue = _amount.value.toDoubleOrNull()
        if (amountValue == null) {
            _error.value = "Por favor, insira um valor válido"
            return
        }
        
        if (amountValue <= 0) {
            _error.value = "O valor deve ser maior que zero"
            return
        }
        
        _uiState.value = CurrencyUiState.Loading
        _error.value = null
        
        viewModelScope.launch {
            try {
                val result = repository.convertCurrency(amountValue, "BRL", "USD")
                _uiState.value = CurrencyUiState.Success(result)
            } catch (e: CurrencyException) {
                _error.value = e.message
                _uiState.value = CurrencyUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}

sealed class CurrencyUiState {
    object Initial : CurrencyUiState()
    object Loading : CurrencyUiState()
    data class Success(val conversion: CurrencyConversion) : CurrencyUiState()
    data class Error(val message: String) : CurrencyUiState()
} 