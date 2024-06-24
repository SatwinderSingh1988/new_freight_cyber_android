package com.app.freightCyber.presentation.test_route_api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RouteInterface {

    @GET("directions/json")
    fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String): Call<DirectionRespponse>
}