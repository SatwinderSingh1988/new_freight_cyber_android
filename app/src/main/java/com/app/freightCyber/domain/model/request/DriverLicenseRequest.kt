package com.app.freightCyber.domain.model.request

data class DriverLicenseRequest(
    val address: String,
    val country: String,
    val license_card_number: String,
    val license_expiry_date: String,
    val license_issuing_state: String,
    val license_number: String,
    val license_type: String,
    val post_code: String
)