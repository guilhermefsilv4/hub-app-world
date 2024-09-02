package br.com.fiap.hubappworld.screens.hub

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.hubappworld.BuildConfig
import br.com.fiap.hubappworld.components.WeatherIcon
import br.com.fiap.hubappworld.components.WeatherText
import br.com.fiap.hubappworld.functions.getWeatherDrawableResourceId
import br.com.fiap.hubappworld.models.Clima
import br.com.fiap.hubappworld.models.ListElement
import br.com.fiap.hubappworld.models.Main
import br.com.fiap.hubappworld.models.Weather
import br.com.fiap.hubappworld.models.WeatherComplete
import br.com.fiap.hubappworld.services.RetrofitFactory
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val API_KEY = BuildConfig.API_KEY

@Composable
fun WeatherScreen(currentLocation: LatLng) {
    var climaResponse by remember { mutableStateOf<Clima?>(null) }
    var clima by remember { mutableStateOf<Weather?>(null) }
    var temperatura by remember { mutableStateOf<Main?>(null) }

    var forecast by remember { mutableStateOf<WeatherComplete?>(null) }
    var listWeather by remember { mutableStateOf(listOf<ListElement>()) }

    val call = RetrofitFactory().getClimaService().getClima(
        currentLocation.latitude,
        currentLocation.longitude,
        API_KEY,
        "pt_br",
        "metric"
    )

    val call2 = RetrofitFactory().getClimaService().getFiveDays(
        currentLocation.latitude,
        currentLocation.longitude,
        API_KEY,
        "pt_br",
        "metric"
    )

    call.enqueue(object : Callback<Clima> {
        override fun onResponse(
            call: Call<Clima>,
            response: Response<Clima>,
        ) {
            climaResponse = response.body()
            if (climaResponse != null) {
                clima = climaResponse!!.weather[0]
                temperatura = climaResponse!!.main
            }
        }

        override fun onFailure(call: Call<Clima>, t: Throwable) {
            Log.i("RESPONSE:", "${t.message}")
        }

    })

    call2.enqueue(object : Callback<WeatherComplete> {
        override fun onResponse(
            call: Call<WeatherComplete>,
            response: Response<WeatherComplete>,
        ) {
            forecast = response.body()
            if (forecast != null) {
                listWeather = forecast!!.list
                Log.i("LISTA", "${forecast!!.list}")
            }

            Log.i("teste", "${response.body()}")
        }

        override fun onFailure(call: Call<WeatherComplete>, t: Throwable) {
            Log.i("RESPONSE:", "${t.message}")
        }
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Temperatura",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier
                .height(380.dp)
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (clima != null) {
                    WeatherIcon(painterResource(id = getWeatherDrawableResourceId(clima!!.icon)))
                }
                clima?.let {
                    WeatherText(it.description)
                }
                temperatura?.let {
                    WeatherText("${temperatura!!.temp} ºC")
                }
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            items(listWeather) {
                Card(
                    modifier = Modifier
                        .height(180.dp)
                        .padding(10.dp)
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        painter = painterResource(id = getWeatherDrawableResourceId(it.weather.get(0).icon)),
                        contentDescription = "Icone do tempo atual"
                    )
                    Text(
                        text = it.dtTxt,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                    Text(
                        text = it.weather.get(0).description,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                    )
                }
            }
        }
    }
}