package com.currencyconverter.shared.cache

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Serializable
data class CachedExchangeRate(
    val fromCurrency: String,
    val toCurrency: String,
    val rate: Double,
    val timestamp: Long
)

class ExchangeRateCache {
    private data class CacheEntry(
        val rate: Double,
        val timestamp: Instant = Clock.System.now()
    )

    private val mutex = Mutex()
    private val cache = mutableMapOf<String, CacheEntry>()
    private val json = Json { 
        ignoreUnknownKeys = true
        isLenient = true
    }
    private val maxSize = 100
    private val expirationTime = 30L // 30 minutos
    
    suspend fun getRate(from: String, to: String): Double? {
        return mutex.withLock {
            val key = createKey(from, to)
            val entry = cache[key] ?: return null

            // Verifica se a entrada expirou
            val now = Clock.System.now()
            val entryTime = entry.timestamp.toLocalDateTime(TimeZone.currentSystemDefault())
            val currentTime = now.toLocalDateTime(TimeZone.currentSystemDefault())
            
            if (currentTime.minute - entryTime.minute > expirationTime) {
                cache.remove(key)
                return null
            }

            entry.rate
        }
    }
    
    suspend fun saveRate(from: String, to: String, rate: Double) {
        mutex.withLock {
            val key = createKey(from, to)
            if (cache.size >= maxSize) {
                // Remove a entrada mais antiga se o cache estiver cheio
                cache.minByOrNull { it.value.timestamp }?.let { cache.remove(it.key) }
            }
            cache[key] = CacheEntry(rate)
        }
    }
    
    private fun createKey(from: String, to: String): String {
        return "$from-$to"
    }
    
    suspend fun clearCache() {
        mutex.withLock {
            cache.clear()
        }
    }
    
    fun getCacheSize(): Int = cache.size
    
    fun getCacheKeys(): Set<String> = cache.keys
} 