package com.app.freightCyber.domain.model.request

data class AdditionalIdentificationRequest(
    val expiry: String,
    val id_type: String ,
    val issue : String?,
    val passport_number : String? ,
    val country_of_issue : String?
)
