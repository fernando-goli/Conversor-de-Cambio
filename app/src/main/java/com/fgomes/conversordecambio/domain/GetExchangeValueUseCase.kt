package com.fgomes.conversordecambio.domain

import com.fgomes.conversordecambio.core.UseCase
import com.fgomes.conversordecambio.data.model.ExchangeResponseValue
import com.fgomes.conversordecambio.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

class GetExchangeValueUseCase(
    private val repository: CoinRepository
): UseCase<String, ExchangeResponseValue>() {

    override suspend fun execute(param: String): Flow<ExchangeResponseValue> {
        return repository.getExchangeValue(param)
    }

}