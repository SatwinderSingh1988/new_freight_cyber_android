package com.app.freightCyber.utils

import androidx.lifecycle.MutableLiveData

object Constants {

    const val MAP_API_KEY = "AIzaSyD5Jt2e9ocVmXovnsOsdmtdhPRkP8m9IhQ"

    // terms & service
    val termsLiveData = MutableLiveData<Boolean>()
    const val DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
    const val DEFAULT_TIME_FORMAT = "hh:mm:ss"

    // End Points
    const val GENERATE_PASSCODE = "/auth/passcode"
    const val SIGNUP = "/auth/login"
    const val LOGIN = "/auth/login"
    const val EMERGENCY_CONTACT = "driver/emergency-contact"
    const val DRIVER_PROFILE = "driver/profile"
    const val ADDITIONAL_IDENTIFICATION = "driver/additional-identification"
    const val DRIVER_LICENSE = "driver-license"
    const val TRANSPORT_OPERATOR = "transport-operator"
    const val DRIVER_AGREEMENT = "driver/agreement"
    const val DRIVER_PROFILE_PRESIGNED_URL = "driver/profile/presigned-url"

}