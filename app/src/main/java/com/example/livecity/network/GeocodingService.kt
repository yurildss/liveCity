package com.example.livecity.network

import com.example.livecity.model.Address
import com.example.livecity.model.GeocodingResponse
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL =  "https://maps.googleapis.com/maps/api/geocode/"

val client = OkHttpClient.Builder()
    .addInterceptor(APIKeyInterceptor())
    .build()

private val retrofit = Retrofit
    .Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .client(client)
    .build()

//https://maps.googleapis.com/maps/api/geocode/json?latlng=-12.2456,-38.9647&key=YOUR_API_KEY
interface GeocodingService{
    @GET("json")
    suspend fun getAddressByGeo(@Query("latlng") latLon: String): GeocodingResponse
}

object GeoCodingApi{
    val geocodingService: GeocodingService by lazy {
        retrofit.create(GeocodingService::class.java)
    }
}
