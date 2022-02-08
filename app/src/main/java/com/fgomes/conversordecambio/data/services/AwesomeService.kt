package com.fgomes.conversordecambio.data.services

import com.fgomes.conversordecambio.data.model.ExchangeResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AwesomeService {

    @GET("/json/last/{coins}")
    suspend fun exchangeValue(@Path("coins") coins: String) : ExchangeResponse
}