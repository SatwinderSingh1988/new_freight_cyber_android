package com.app.freightCyber.domain.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DriverLicenseResponse(
    val `data`: DriverLicenseData?,
    val message: String?,
    val status: Int?
): Parcelable

@Parcelize
data class DriverLicenseData(
    val address: String?,
    val country: String?,
    val dma_driver_profile_id: Int?,
    val id: Int?,
    val license_card_number: String?,
    val license_expiry_date: String?,
    val license_issuing_state: String?,
    val license_number: String?,
    val license_type: String?,
    val post_code: String?
) : Parcelable

