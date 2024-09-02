package br.com.fiap.hubappworld.screens.hub

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.hubappworld.R
import br.com.fiap.hubappworld.components.CardHub
import br.com.fiap.hubappworld.functions.navigate

@Composable
fun HubScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFD4DCFF)),
        contentAlignment = Alignment.Center
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(-35.dp)) {
            CardHub(
                painterResource(id = R.drawable.clima),
                "Icone do clima",
                handleClick = { navigate(navController, "clima") }
            )
//            CardHub(painterResource(id = R.drawable.monetization_on_24), "Icone de dinheiro")
        }
    }
}