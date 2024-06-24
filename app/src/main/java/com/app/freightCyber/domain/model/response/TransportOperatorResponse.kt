package com.app.freightCyber.domain.model.response

data class TransportOperatorResponse(
    val `data`: List<TransportData>?,
    val status: Int? ,
    val message : String?
)

data class TransportData(
    val company_name: String?,
    val country: String?,
    val creation_date: String?,
    val dma_driver_profile_id: Int?,
    val id: Int?
)