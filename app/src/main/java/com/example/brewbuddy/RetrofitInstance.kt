package com.example.brewbuddy
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.brewbuddy.data.remote.api.CoffeeApiService
object RetrofitInstance {
    private const val BASE_URL = "https://api.sampleapis.com/"

    val api: CoffeeApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoffeeApiService::class.java)
    }
}
