package com.app.freightCyber.domain.model.request


data class DriverProfileRequest(
    val date_of_birth: String,
    val first_name: String,
    val last_name: String,
    val title: String ,
    val profile_photo: String? = "" ,
)