package br.com.fiap.hubappworld.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun WeatherIcon(painterResource: Painter) {
    Image(
        modifier = Modifier.size(200.dp),
        painter = painterResource,
        contentDescription = "icone da previsão do tempo"
    )
}