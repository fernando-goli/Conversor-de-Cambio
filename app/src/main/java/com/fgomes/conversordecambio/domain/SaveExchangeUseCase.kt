package com.fgomes.conversordecambio.domain

import com.fgomes.conversordecambio.core.UseCase
import com.fgomes.conversordecambio.data.model.ExchangeResponseValue
import com.fgomes.conversordecambio.data.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveExchangeUseCase(
    private val repository: CoinRepository
) : UseCase.NoSource<ExchangeResponseValue>() {

    override suspend fun execute(param: ExchangeResponseValue): Flow<Unit> {
        return flow {
            repository.save(param)
            emit(Unit)
        }
    }

}