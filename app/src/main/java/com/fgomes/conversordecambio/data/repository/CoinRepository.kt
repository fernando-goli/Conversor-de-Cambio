package com.fgomes.conversordecambio.data.repository

import com.fgomes.conversordecambio.data.model.ExchangeResponseValue
import kotlinx.coroutines.flow.Flow


interface CoinRepository {

    suspend fun getExchangeValue(coins: String) : Flow<ExchangeResponseValue>

    suspend fun save(exchangeResponseValue: ExchangeResponseValue)
    fun list (): Flow<List<ExchangeResponseValue>>
}