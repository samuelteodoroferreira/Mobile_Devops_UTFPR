package com.currencyconverter.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.currencyconverter.shared.di.initKoin
import com.currencyconverter.shared.ui.CurrencyScreen
import com.currencyconverter.shared.viewmodel.CurrencyViewModel
import org.koin.core.context.GlobalContext
import org.koin.core.component.get

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inicializar Koin (apenas uma vez)
        if (GlobalContext.getOrNull() == null) {
            initKoin()
        }
        
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Obter o ViewModel manualmente do Koin
                    val viewModel: CurrencyViewModel = org.koin.java.KoinJavaComponent.get(CurrencyViewModel::class.java)
                    CurrencyScreen(viewModel = viewModel)
                }
            }
        }
    }
} 