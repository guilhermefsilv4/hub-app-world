package br.com.fiap.hubappworld.services

import br.com.fiap.hubappworld.models.Clima
import br.com.fiap.hubappworld.models.WeatherComplete
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ClimaService {

    @GET("weather")
    fun getClima(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("lang") lang: String,
        @Query("units") units: String
    ): Call<Clima>

    @GET("forecast")
    fun getFiveDays(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("lang") lang: String,
        @Query("units") units: String
    ): Call<WeatherComplete>
}