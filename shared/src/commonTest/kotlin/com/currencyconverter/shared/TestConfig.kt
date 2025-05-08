package com.currencyconverter.shared

import com.currencyconverter.shared.repository.CurrencyRepository
import com.currencyconverter.shared.viewmodel.CurrencyViewModel
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

object TestConfig {
    fun setupKoin() {
        stopKoin()
        startKoin {
            modules(module {
                single { mockk<CurrencyRepository>(relaxed = true) }
                single { CurrencyViewModel() }
            })
        }
    }
    
    fun tearDownKoin() {
        stopKoin()
    }
} 