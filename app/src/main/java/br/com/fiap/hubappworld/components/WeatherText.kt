package br.com.fiap.hubappworld.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun WeatherText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    )
}