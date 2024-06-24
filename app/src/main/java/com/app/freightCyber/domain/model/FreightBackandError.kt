package com.app.freightCyber.domain.model

import com.google.gson.annotations.SerializedName

data class FreightBackandError(
    @SerializedName("error" , alternate = ["message"])
    val error : String ,
    val status : Int
)
