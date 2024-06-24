package com.app.freightCyber.domain.model.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmergencyContactResponse(
    val `data`: EmergencyData?,
    val message: String?,
    val status: Int?
) : Parcelable

@Parcelize
data class EmergencyData(
    val additional_identification: AdditionalIdentification?,
    val agreement_ids: List<String>?,
    val agreement_status: String?,
    val archive_date: String?,
    val auth_user_id: String?,
    val creation_date: String?,
    val date_of_birth: String?,
    val delete_date: String?,
    val driver_udi: String?,
    val email: String?,
    val emerg_contact_email: String?,
    val emerg_contact_full_name: String?,
    val emerg_contact_full_phone: String?,
    val emerg_contact_relationship: String?,
    val first_name: String?,
    val id: Int?,
    val last_name: String?,
    val modification_date: String?,
    val phone: String?,
    val profile_photo: String?,
    val status: String?,
    val title: String?
) : Parcelable