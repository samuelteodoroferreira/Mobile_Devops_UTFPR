package com.currencyconverter.shared.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 2000)
    )
    
    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = tween(durationMillis = 1000)
    )
    
    LaunchedEffect(key1 = true) {
        startAnimation = true
        kotlinx.coroutines.delay(3000)
        onSplashFinished()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ícone da moeda
            Image(
                painter = painterResource("drawable/coin.xml"),
                contentDescription = "Moeda",
                modifier = Modifier
                    .size(120.dp)
                    .scale(scaleAnim.value)
                    .alpha(alphaAnim.value)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Texto de boas-vindas
            Text(
                text = "Conversor de Moedas",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.alpha(alphaAnim.value)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Bem-vindo!",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.alpha(alphaAnim.value)
            )
        }
    }
} 