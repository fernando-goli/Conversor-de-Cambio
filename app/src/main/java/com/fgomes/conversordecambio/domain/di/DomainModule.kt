package com.fgomes.conversordecambio.domain.di

import com.fgomes.conversordecambio.domain.GetExchangeValueUseCase
import com.fgomes.conversordecambio.domain.ListExchangeUseCase
import com.fgomes.conversordecambio.domain.SaveExchangeUseCase
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load(){
        loadKoinModules(useCaseModules())
    }

    private fun useCaseModules(): Module {
        return module {
            factory { GetExchangeValueUseCase(get()) }
            factory { SaveExchangeUseCase(get() ) }
            factory { ListExchangeUseCase(get() ) }
        }
    }
}