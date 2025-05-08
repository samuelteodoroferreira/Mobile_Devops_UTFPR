package com.currencyconverter.android

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.currencyconverter.shared.model.CurrencyConversion
import com.currencyconverter.shared.ui.CurrencyScreen
import com.currencyconverter.shared.viewmodel.CurrencyUiState
import com.currencyconverter.shared.viewmodel.CurrencyViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.dsl.module

@RunWith(AndroidJUnit4::class)
class CurrencyScreenAndroidTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    
    private val viewModel = mockk<CurrencyViewModel>(relaxed = true)
    private val uiState = MutableStateFlow<CurrencyUiState>(CurrencyUiState.Initial)
    private val amount = MutableStateFlow("")
    
    @Before
    fun setup() {
        coEvery { viewModel.uiState } returns uiState
        coEvery { viewModel.amount } returns amount
        
        startKoin {
            modules(module {
                single { viewModel }
            })
        }
    }
    
    @Test
    fun test_initial_screen_state() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("currency_screen").assertExists()
        composeTestRule.onNodeWithTag("title").assertExists()
        composeTestRule.onNodeWithTag("amount_input").assertExists()
        composeTestRule.onNodeWithTag("conversion_buttons").assertExists()
    }
    
    @Test
    fun test_input_field_interaction() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("amount_input")
            .performTextInput("100.00")
            .assertTextContains("100.00")
    }
    
    @Test
    fun test_convert_to_real_button_click() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("convert_to_real_button").performClick()
    }
    
    @Test
    fun test_convert_to_dollar_button_click() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("convert_to_dollar_button").performClick()
    }
    
    @Test
    fun test_loading_state_display() {
        uiState.value = CurrencyUiState.Loading
        
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("loading_indicator").assertExists()
    }
    
    @Test
    fun test_success_state_display() {
        val conversion = CurrencyConversion(
            fromCurrency = "USD",
            toCurrency = "BRL",
            amount = 100.0,
            convertedAmount = 500.0
        )
        
        uiState.value = CurrencyUiState.Success(conversion)
        
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("success_container").assertExists()
        composeTestRule.onNodeWithTag("result_title").assertExists()
        composeTestRule.onNodeWithTag("converted_amount").assertExists()
        composeTestRule.onNodeWithTag("exchange_rate").assertExists()
    }
    
    @Test
    fun test_error_state_display() {
        uiState.value = CurrencyUiState.Error("Erro de conexão")
        
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("error_message")
            .assertExists()
            .assertTextEquals("Erro de conexão")
    }
    
    @Test
    fun test_input_validation_invalid_characters() {
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("amount_input")
            .performTextInput("abc")
            .assertTextContains("abc")
    }
    
    @Test
    fun test_currency_format_display() {
        val conversion = CurrencyConversion(
            fromCurrency = "USD",
            toCurrency = "BRL",
            amount = 1234.56,
            convertedAmount = 6173.45
        )
        
        uiState.value = CurrencyUiState.Success(conversion)
        
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("converted_amount")
            .assertExists()
            .assertTextContains("R$ 6.173,45")
    }
    
    @Test
    fun test_exchange_rate_display() {
        val conversion = CurrencyConversion(
            fromCurrency = "USD",
            toCurrency = "BRL",
            amount = 100.0,
            convertedAmount = 500.0
        )
        
        uiState.value = CurrencyUiState.Success(conversion)
        
        composeTestRule.setContent {
            CurrencyScreen(viewModel = viewModel)
        }
        
        composeTestRule.onNodeWithTag("exchange_rate")
            .assertExists()
            .assertTextContains("Taxa de câmbio: 5.0")
    }
} 