package com.currencyconverter.shared.cache

import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ExchangeRateCacheTest {
    private val cache = ExchangeRateCache()
    
    @Test
    fun `test save and get rate`() = runTest {
        // Salva uma taxa
        cache.saveRate("USD", "BRL", 5.0)
        
        // Verifica se a taxa foi salva corretamente
        val rate = cache.getRate("USD", "BRL")
        assertEquals(5.0, rate)
    }
    
    @Test
    fun `test expired rate`() = runTest {
        // Salva uma taxa
        cache.saveRate("USD", "BRL", 5.0)
        
        // Simula passagem do tempo
        Thread.sleep(31 * 60 * 1000) // 31 minutos
        
        // Verifica se a taxa expirou
        val rate = cache.getRate("USD", "BRL")
        assertNull(rate)
    }
    
    @Test
    fun `test clear cache`() = runTest {
        // Salva algumas taxas
        cache.saveRate("USD", "BRL", 5.0)
        cache.saveRate("EUR", "BRL", 6.0)
        
        // Limpa o cache
        cache.clearCache()
        
        // Verifica se as taxas foram removidas
        assertNull(cache.getRate("USD", "BRL"))
        assertNull(cache.getRate("EUR", "BRL"))
    }
} 