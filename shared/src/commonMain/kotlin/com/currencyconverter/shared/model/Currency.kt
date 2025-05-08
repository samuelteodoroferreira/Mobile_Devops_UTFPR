package com.currencyconverter.shared.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponse(
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>,
    val error: String? = null
)

@Serializable
data class CurrencyConversion(
    val fromCurrency: String,
    val toCurrency: String,
    val amount: Double,
    val convertedAmount: Double
) 