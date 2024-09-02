package br.com.fiap.hubappworld.models

import com.google.gson.annotations.SerializedName

data class Clima(
    val coord: Coord,
    val weather: List<Weather>,
    val main: Main,
    val visibility: Long,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long
)

data class Clouds(
    val all: Long
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Main(
    val temp: Double,

    @SerializedName("feels_like") val feelsLike: Double,

    @SerializedName("temp_min") val tempMin: Double,

    @SerializedName("temp_max") val tempMax: Double,

    val pressure: Long,
    val humidity: Long,

    @SerializedName("sea_level") val seaLevel: Long,

    @SerializedName("grnd_level") val grndLevel: Long
)

data class Sys(
    val type: Long,
    val id: Long,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double,
    val deg: Long
)

data class WeatherComplete (
    val cod: String,
    val list: List<ListElement>
)

data class ListElement (
    val dt: Long,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Long,
    val pop: Double,
    val sys: Sys,

    @SerializedName("dt_txt") val dtTxt: String,

    val rain: Rain? = null
)

data class Rain (
    @SerializedName("3h") val the3H: Double
)