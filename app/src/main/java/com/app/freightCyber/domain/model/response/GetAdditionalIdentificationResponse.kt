package com.app.freightCyber.domain.model.response

data class GetAdditionalIdentificationResponse(
    val `data`:  AdditionalIdentification,
    val message: String,
    val status: Int
)
