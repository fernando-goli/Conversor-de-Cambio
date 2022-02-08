package com.fgomes.conversordecambio.data.model

data class ErrorResponse(
    val status: Long,
    val code: String,
    val message: String
)
