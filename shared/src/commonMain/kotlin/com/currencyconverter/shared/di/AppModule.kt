package com.currencyconverter.shared.di

import com.currencyconverter.shared.cache.ExchangeRateCache
import com.currencyconverter.shared.repository.CurrencyRepository
import com.currencyconverter.shared.viewmodel.CurrencyViewModel
import org.koin.dsl.module

val appModule = module {
    single { ExchangeRateCache() }
    single { CurrencyRepository() }
    single { CurrencyViewModel() }
} 