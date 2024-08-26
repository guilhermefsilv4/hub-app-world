package br.com.fiap.hubappworld.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HubScreen() {
    Text("Teste")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HubPreview() {
    HubScreen()
}