package com.app.freightCyber.domain.model.request

data class EmergencyContactRequest(
    val emerg_contact_email: String,
    val emerg_contact_full_name: String,
    val emerg_contact_full_phone: String,
    val emerg_contact_relationship: String
)

