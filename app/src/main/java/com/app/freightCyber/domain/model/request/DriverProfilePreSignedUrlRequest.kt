package com.app.freightCyber.domain.model.request

data class DriverProfilePreSignedUrlRequest(
    val contentType: String,
    val filename: String
)