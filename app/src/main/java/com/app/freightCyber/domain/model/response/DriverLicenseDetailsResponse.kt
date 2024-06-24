package com.app.freightCyber.domain.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DriverLicenseDetailsResponse(
    val `data`: List<DriverLicenseData>,
    val message: String,
    val status: Int
) : Parcelable
