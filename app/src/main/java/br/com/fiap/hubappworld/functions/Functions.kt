package br.com.fiap.hubappworld.functions

import androidx.navigation.NavController
import br.com.fiap.hubappworld.R

fun getWeatherDrawableResourceId(name: String): Int {
    return when (name) {
        "01d" -> R.drawable.clima01d2x
        "02d" -> R.drawable.clima02d2x
        "03d" -> R.drawable.clima03d2x
        "04d" -> R.drawable.clima04d2x
        "09d" -> R.drawable.clima09d2x
        "10d" -> R.drawable.clima10d2x
        "11d" -> R.drawable.clima11d2x
        "13d" -> R.drawable.clima13d2x
        "50d" -> R.drawable.clima50d2x

        "01n" -> R.drawable.clima01n2x
        "02n" -> R.drawable.clima02n2x
        "03n" -> R.drawable.clima03n2x
        "04n" -> R.drawable.clima04n2x
        "09n" -> R.drawable.clima09n2x
        "10n" -> R.drawable.clima10n2x
        "11n" -> R.drawable.clima11n2x
        "13n" -> R.drawable.clima13n2x
        "50n" -> R.drawable.clima50n2x

        else -> R.drawable.clima
    }
}

fun navigate(navController: NavController, route: String) {
    navController.navigate(route)
}