package br.com.fiap.hubappworld.screens.hub

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.hubappworld.BuildConfig
import br.com.fiap.hubappworld.functions.getWeatherDrawableResourceId
import br.com.fiap.hubappworld.models.Clima
import br.com.fiap.hubappworld.models.ListElement
import br.com.fiap.hubappworld.models.Main
import br.com.fiap.hubappworld.models.Weather
import br.com.fiap.hubappworld.services.RetrofitFactory
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val API_KEY = BuildConfig.API_KEY

@Composable
fun WeatherScreen(currentLocation: LatLng) {
    var clima by remember { mutableStateOf<Clima?>(null) }
    var climaMesmo by remember { mutableStateOf<Weather?>(null) }
    var temperatura by remember { mutableStateOf<Main?>(null) }

    var forecast by remember { mutableStateOf(listOf<ListElement>()) }

    val call = RetrofitFactory().getClimaService().getClima(
        currentLocation.latitude,
        currentLocation.longitude,
        API_KEY,
        "pt_br",
        "metric"
    )

//    val call2 = RetrofitFactory().getClimaService().getFiveDays(
//        currentLocation.latitude,
//        currentLocation.longitude,
//        API_KEY,
//        "pt_br",
//        "metric"
//    )

    call.enqueue(object : Callback<Clima> {
        override fun onResponse(
            call: Call<Clima>,
            response: Response<Clima>
        ) {
            clima = response.body()
            if (clima != null) {
                climaMesmo = clima!!.weather[0]
                temperatura = clima!!.main
            }
        }

        override fun onFailure(call: Call<Clima>, t: Throwable) {
            Log.i("RESPONSE:", "${t.message}")
        }

    })

//    call2.enqueue(object : Callback<List<ListElement>> {
//        override fun onResponse(
//            call: Call<List<ListElement>>,
//            response: Response<List<ListElement>>
//        ) {
//            Log.i("teste", "${response.body()}")
//        }
//
//        override fun onFailure(call: Call<List<ListElement>>, t: Throwable) {
//            Log.i("RESPONSE:", "${t.message}")
//        }
//
//    })

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
                .height(400.dp)
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (climaMesmo != null) {
                    Image(
                        modifier = Modifier.align(Alignment.CenterHorizontally).size(200.dp),
                        painter = painterResource(
                            id = getWeatherDrawableResourceId(
                                climaMesmo!!.icon
                            )
                        ),
                        contentDescription = "icone da previsão do tempo"
                    )
                }
                climaMesmo?.let {
                    Text(
                        text = it.description,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                }
                temperatura?.let {
                    Text(
                        text = "${temperatura!!.temp} ºC",
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                }
            }
        }
    }
}