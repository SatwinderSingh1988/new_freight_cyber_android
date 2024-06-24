package com.app.freightCyber.domain.model.response

import com.google.gson.annotations.SerializedName

data class SimpleApiResponse (
    @SerializedName("message")
    val message: String = "",
    @SerializedName("status")
    val status: Int?
)
