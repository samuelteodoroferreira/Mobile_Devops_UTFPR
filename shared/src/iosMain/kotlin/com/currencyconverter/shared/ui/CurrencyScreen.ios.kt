package com.currencyconverter.shared.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.currencyconverter.shared.viewmodel.CurrencyUiState
import com.currencyconverter.shared.viewmodel.CurrencyViewModel

@Composable
actual fun CurrencyScreen(viewModel: CurrencyViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val amount by viewModel.amount.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("currency_screen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Conversor de Moedas",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.testTag("title")
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { viewModel.updateAmount(it) },
            label = { Text("Valor") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("amount_input")
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("conversion_buttons"),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { viewModel.convertToReal() },
                modifier = Modifier
                    .weight(1f)
                    .testTag("convert_to_real_button")
            ) {
                Text("Converter para Real")
            }

            Button(
                onClick = { viewModel.convertToDollar() },
                modifier = Modifier
                    .weight(1f)
                    .testTag("convert_to_dollar_button")
            ) {
                Text("Converter para Dólar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is CurrencyUiState.Initial -> {
                Text(
                    text = "Digite um valor para converter",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.testTag("initial_message")
                )
            }
            is CurrencyUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.testTag("loading_indicator")
                )
            }
            is CurrencyUiState.Success -> {
                val conversion = state.conversion
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.testTag("success_container")
                ) {
                    Text(
                        text = "Resultado da Conversão",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.testTag("result_title")
                    )
                    Text(
                        text = "${conversion.toCurrency} ${conversion.convertedAmount}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.testTag("converted_amount")
                    )
                    Text(
                        text = "Taxa de câmbio: ${conversion.convertedAmount / conversion.amount}",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.testTag("exchange_rate")
                    )
                }
            }
            is CurrencyUiState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.testTag("error_message")
                )
            }
        }
    }
} 