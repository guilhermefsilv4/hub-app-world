package br.com.fiap.hubappworld.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    private val url = "https://api.openweathermap.org/data/2.5/"

    private val retrofitFactory = Retrofit
        .Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getClimaService(): ClimaService {
        return retrofitFactory.create(ClimaService::class.java)
    }
}