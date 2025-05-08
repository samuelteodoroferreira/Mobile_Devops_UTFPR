package com.currencyconverter.shared.repository

import com.currencyconverter.shared.cache.ExchangeRateCache
import com.currencyconverter.shared.model.CurrencyConversion
import com.currencyconverter.shared.model.CurrencyResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.timeout.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.time.Duration.Companion.seconds

class CurrencyRepository : KoinComponent {
    private val cache: ExchangeRateCache by inject()
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 10.seconds.inWholeMilliseconds
            connectTimeoutMillis = 5.seconds.inWholeMilliseconds
        }
    }

    suspend fun getExchangeRate(from: String, to: String): Double {
        try {
            // Validação das moedas
            if (from !in validCurrencies || to !in validCurrencies) {
                throw InvalidCurrencyException("Moeda inválida. Use apenas USD ou BRL.")
            }
            
            // Tenta obter do cache primeiro
            cache.getRate(from, to)?.let { return it }
            
            // Se não estiver no cache, busca da API
            val response: CurrencyResponse = client.get("https://api.exchangerate-api.com/v4/latest/$from") {
                parameter("symbols", to)
            }.body()
            
            if (!response.success) {
                throw ApiException("Erro na resposta da API: ${response.error ?: "Erro desconhecido"}")
            }
            
            val rate = response.rates[to] ?: throw ApiException("Taxa de câmbio não encontrada")
            
            // Salva no cache
            cache.saveRate(from, to, rate)
            
            return rate
        } catch (e: Exception) {
            when (e) {
                is InvalidCurrencyException, is ApiException -> throw e
                else -> throw NetworkException("Erro de conexão: ${e.message}")
            }
        }
    }

    suspend fun convertCurrency(amount: Double, from: String, to: String): CurrencyConversion {
        try {
            // Validação do valor
            if (amount <= 0) {
                throw InvalidAmountException("O valor deve ser maior que zero")
            }
            
            val rate = getExchangeRate(from, to)
            val convertedAmount = amount * rate
            
            return CurrencyConversion(
                fromCurrency = from,
                toCurrency = to,
                amount = amount,
                convertedAmount = convertedAmount
            )
        } catch (e: Exception) {
            when (e) {
                is InvalidAmountException, is InvalidCurrencyException, is ApiException, is NetworkException -> throw e
                else -> throw ConversionException("Erro ao converter moeda: ${e.message}")
            }
        }
    }
    
    companion object {
        private val validCurrencies = setOf("USD", "BRL")
    }
}

sealed class CurrencyException(message: String) : Exception(message)
class InvalidCurrencyException(message: String) : CurrencyException(message)
class InvalidAmountException(message: String) : CurrencyException(message)
class ApiException(message: String) : CurrencyException(message)
class NetworkException(message: String) : CurrencyException(message)
class ConversionException(message: String) : CurrencyException(message) 