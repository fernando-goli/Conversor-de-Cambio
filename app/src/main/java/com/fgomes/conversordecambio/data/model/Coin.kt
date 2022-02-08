package com.fgomes.conversordecambio.data.model

import java.util.*

enum class Coin(val locale: Locale) {
    USD(Locale.US),
    EUR(Locale.FRANCE),
    CAD(Locale.CANADA),
    BRL(Locale("pt", "BR")),
    AUS(Locale("en","AU"))
    ;

    companion object {
        fun getByName(name: String) = values().find{ it.name == name } ?: BRL
    }

}