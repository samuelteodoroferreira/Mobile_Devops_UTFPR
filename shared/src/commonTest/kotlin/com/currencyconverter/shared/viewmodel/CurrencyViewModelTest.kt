package com.currencyconverter.shared.viewmodel

import com.currencyconverter.shared.model.CurrencyConversion
import com.currencyconverter.shared.repository.CurrencyRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import kotlin.test.*

class CurrencyViewModelTest {
    private val repository = mockk<CurrencyRepository>()
    private lateinit var viewModel: CurrencyViewModel
    
    @BeforeTest
    fun setup() {
        startKoin {
            modules(module {
                single { repository }
            })
        }
        viewModel = CurrencyViewModel()
    }
    
    @AfterTest
    fun tearDown() {
        stopKoin()
    }
    
    @Test
    fun `test initial state`() = runTest {
        val state = viewModel.uiState.first()
        assertTrue(state is CurrencyUiState.Initial)
        assertEquals("", viewModel.amount.first())
    }
    
    @Test
    fun `test update amount with valid value`() = runTest {
        viewModel.updateAmount("100.00")
        assertEquals("100.00", viewModel.amount.first())
    }
    
    @Test
    fun `test update amount with invalid value`() = runTest {
        viewModel.updateAmount("abc")
        assertEquals("abc", viewModel.amount.first())
        viewModel.convertToReal()
        val state = viewModel.uiState.first()
        assertTrue(state is CurrencyUiState.Initial)
    }
    
    @Test
    fun `test convert to real success`() = runTest {
        val expectedConversion = CurrencyConversion(
            fromCurrency = "USD",
            toCurrency = "BRL",
            amount = 100.0,
            convertedAmount = 500.0
        )
        
        coEvery { repository.convertCurrency(100.0, "USD", "BRL") } returns expectedConversion
        
        viewModel.updateAmount("100.00")
        viewModel.convertToReal()
        
        val state = viewModel.uiState.first()
        assertTrue(state is CurrencyUiState.Success)
        assertEquals(expectedConversion, (state as CurrencyUiState.Success).conversion)
    }
    
    @Test
    fun `test convert to dollar success`() = runTest {
        val expectedConversion = CurrencyConversion(
            fromCurrency = "BRL",
            toCurrency = "USD",
            amount = 100.0,
            convertedAmount = 20.0
        )
        
        coEvery { repository.convertCurrency(100.0, "BRL", "USD") } returns expectedConversion
        
        viewModel.updateAmount("100.00")
        viewModel.convertToDollar()
        
        val state = viewModel.uiState.first()
        assertTrue(state is CurrencyUiState.Success)
        assertEquals(expectedConversion, (state as CurrencyUiState.Success).conversion)
    }
    
    @Test
    fun `test conversion error`() = runTest {
        coEvery { repository.convertCurrency(100.0, "USD", "BRL") } throws Exception("Erro de conexão")
        
        viewModel.updateAmount("100.00")
        viewModel.convertToReal()
        
        val state = viewModel.uiState.first()
        assertTrue(state is CurrencyUiState.Error)
        assertEquals("Erro de conexão", (state as CurrencyUiState.Error).message)
    }
    
    @Test
    fun `test loading state during conversion`() = runTest {
        coEvery { repository.convertCurrency(100.0, "USD", "BRL") } returns mockk()
        
        viewModel.updateAmount("100.00")
        viewModel.convertToReal()
        
        val loadingState = viewModel.uiState.first()
        assertTrue(loadingState is CurrencyUiState.Loading)
    }
    
    @Test
    fun `test empty amount conversion attempt`() = runTest {
        viewModel.updateAmount("")
        viewModel.convertToReal()
        
        val state = viewModel.uiState.first()
        assertTrue(state is CurrencyUiState.Initial)
    }
    
    @Test
    fun `test zero amount conversion attempt`() = runTest {
        viewModel.updateAmount("0.00")
        viewModel.convertToReal()
        
        val state = viewModel.uiState.first()
        assertTrue(state is CurrencyUiState.Initial)
    }
    
    @Test
    fun `test negative amount conversion attempt`() = runTest {
        viewModel.updateAmount("-100.00")
        viewModel.convertToReal()
        
        val state = viewModel.uiState.first()
        assertTrue(state is CurrencyUiState.Initial)
    }
} 