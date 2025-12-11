
package com.example.brewbuddy.data.remote.api
import com.example.brewbuddy.data.remote.api.CoffeeApiModel
import retrofit2.http.GET

interface CoffeeApiService {
    @GET("coffee/hot")
    suspend fun getHotCoffees(): List<CoffeeApiModel>

    @GET("coffee/iced")
    suspend fun getIcedCoffees(): List<CoffeeApiModel>
}
