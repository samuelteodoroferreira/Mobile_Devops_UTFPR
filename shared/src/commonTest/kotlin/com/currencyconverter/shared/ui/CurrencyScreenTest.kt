package com.currencyconverter.shared.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.currencyconverter.shared.model.CurrencyConversion
import com.currencyconverter.shared.viewmodel.CurrencyUiState
import com.currencyconverter.shared.viewmodel.CurrencyViewModel
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class CurrencyScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    
    private val viewModel = mockk<CurrencyViewModel>(relaxed = true)
    
    @Test
    fun `test initial screen state`() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithText("Conversor de Moedas").assertExists()
        composeTestRule.onNodeWithText("Digite um valor para converter").assertExists()
        composeTestRule.onNodeWithText("Converter para Real").assertExists()
        composeTestRule.onNodeWithText("Converter para Dólar").assertExists()
    }
    
    @Test
    fun `test input field interaction`() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("amount_input")
            .performTextInput("100.00")
            .assertTextEquals("100.00")
    }
    
    @Test
    fun `test convert to real button click`() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithText("Converter para Real").performClick()
        // Verificar se o método do ViewModel foi chamado
    }
    
    @Test
    fun `test convert to dollar button click`() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithText("Converter para Dólar").performClick()
        // Verificar se o método do ViewModel foi chamado
    }
    
    @Test
    fun `test loading state display`() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        coEvery { viewModel.uiState } returns CurrencyUiState.Loading
        
        composeTestRule.onNodeWithTag("loading_indicator").assertExists()
    }
    
    @Test
    fun `test success state display`() {
        val conversion = CurrencyConversion(
            fromCurrency = "USD",
            toCurrency = "BRL",
            amount = 100.0,
            convertedAmount = 500.0
        )
        
        coEvery { viewModel.uiState } returns CurrencyUiState.Success(conversion)
        
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithText("Resultado da Conversão").assertExists()
        composeTestRule.onNodeWithText("R$ 500,00").assertExists()
    }
    
    @Test
    fun `test error state display`() {
        coEvery { viewModel.uiState } returns CurrencyUiState.Error("Erro de conexão")
        
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithText("Erro de conexão").assertExists()
    }
    
    @Test
    fun `test input validation - invalid characters`() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("amount_input")
            .performTextInput("abc")
            .assertTextEquals("abc")
    }
    
    @Test
    fun `test currency format display`() {
        val conversion = CurrencyConversion(
            fromCurrency = "USD",
            toCurrency = "BRL",
            amount = 1234.56,
            convertedAmount = 6173.45
        )
        
        coEvery { viewModel.uiState } returns CurrencyUiState.Success(conversion)
        
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithText("R$ 6.173,45").assertExists()
    }
    
    @Test
    fun `test exchange rate display`() {
        val conversion = CurrencyConversion(
            fromCurrency = "USD",
            toCurrency = "BRL",
            amount = 100.0,
            convertedAmount = 500.0
        )
        
        coEvery { viewModel.uiState } returns CurrencyUiState.Success(conversion)
        
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithText("Taxa de câmbio: 5.0").assertExists()
    }
} 