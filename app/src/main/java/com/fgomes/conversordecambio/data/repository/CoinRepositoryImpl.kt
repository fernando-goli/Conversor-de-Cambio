package com.fgomes.conversordecambio.data.repository

import com.fgomes.conversordecambio.core.exceptions.RemoteException
import com.fgomes.conversordecambio.data.database.AppDatabase
import com.fgomes.conversordecambio.data.model.ErrorResponse
import com.fgomes.conversordecambio.data.model.ExchangeResponseValue
import com.fgomes.conversordecambio.data.services.AwesomeService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class CoinRepositoryImpl(
    appDatabase: AppDatabase,
    private val service: AwesomeService
): CoinRepository {

    private val dao = appDatabase.exchangeDao()

    override suspend fun getExchangeValue(coins: String) = flow {
        try {
            val exchangeValue = service.exchangeValue(coins)
            val exchange = exchangeValue.values.first()
            emit(exchange)
        } catch(e: HttpException){
            val json = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(json, ErrorResponse::class.java)
            throw RemoteException(errorResponse.message)
        }
    }

    override suspend fun save(exchangeResponseValue: ExchangeResponseValue) {
        dao.save(exchangeResponseValue)
    }

    override fun list(): Flow<List<ExchangeResponseValue>> {
        return dao.findAll()
    }

}