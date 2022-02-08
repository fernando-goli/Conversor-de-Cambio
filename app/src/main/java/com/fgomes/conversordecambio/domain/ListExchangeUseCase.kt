package com.fgomes.conversordecambio.domain

import com.fgomes.conversordecambio.core.UseCase
import com.fgomes.conversordecambio.data.model.ExchangeResponseValue
import com.fgomes.conversordecambio.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ListExchangeUseCase(
    private val repository: CoinRepository
) : UseCase.NoParam<List<ExchangeResponseValue>>() {

    override suspend fun execute(): Flow<List<ExchangeResponseValue>> {
        return repository.list()

    }
}