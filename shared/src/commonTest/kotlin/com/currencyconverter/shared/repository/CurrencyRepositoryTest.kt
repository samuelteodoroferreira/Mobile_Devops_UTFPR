package com.currencyconverter.shared.repository

import com.currencyconverter.shared.cache.ExchangeRateCache
import com.currencyconverter.shared.model.CurrencyConversion
import com.currencyconverter.shared.model.CurrencyResponse
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class CurrencyRepositoryTest {
    private val cache = mockk<ExchangeRateCache>()
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
            })
        }
    }
    private val repository = CurrencyRepository()
    
    @Test
    fun `test convert USD to BRL with cache`() = runTest {
        // Configura o mock do cache
        coEvery { cache.getRate("USD", "BRL") } returns 5.0
        
        val result = repository.convertCurrency(1.0, "USD", "BRL")
        
        assertTrue(result is CurrencyConversion)
        assertEquals("USD", result.fromCurrency)
        assertEquals("BRL", result.toCurrency)
        assertEquals(1.0, result.amount)
        assertEquals(5.0, result.convertedAmount)
        
        coVerify { cache.getRate("USD", "BRL") }
    }
    
    @Test
    fun `test convert BRL to USD with cache`() = runTest {
        // Configura o mock do cache
        coEvery { cache.getRate("BRL", "USD") } returns 0.2
        
        val result = repository.convertCurrency(1.0, "BRL", "USD")
        
        assertTrue(result is CurrencyConversion)
        assertEquals("BRL", result.fromCurrency)
        assertEquals("USD", result.toCurrency)
        assertEquals(1.0, result.amount)
        assertEquals(0.2, result.convertedAmount)
        
        coVerify { cache.getRate("BRL", "USD") }
    }
    
    @Test
    fun `test convert with invalid amount`() = runTest {
        try {
            repository.convertCurrency(-1.0, "USD", "BRL")
            assertTrue(false, "Deveria lançar uma exceção")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("valor inválido") == true)
        }
    }
    
    @Test
    fun `test convert with invalid currency`() = runTest {
        try {
            repository.convertCurrency(1.0, "INVALID", "BRL")
            assertTrue(false, "Deveria lançar uma exceção")
        } catch (e: Exception) {
            assertTrue(e.message?.contains("moeda inválida") == true)
        }
    }
} 