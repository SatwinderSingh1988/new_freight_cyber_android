package com.app.freightCyber.domain.model.response
data class LoginSignUpResponse(
    val `data`: Data?,
    val message: String?,
    val status: Int?
)

data class Data(
    val access_token: String?,
    val refresh_token: String?,
    val user: User?
)

data class User(
    val aud: String?,
    var email: String?,
    val email_verified: Boolean,
    val exp: Int?,
    val iat: Int?,
    val iss: String?,
    var name: String?,
    val nickname: String?,
    val picture: String?,
    val sub: String?,
    val updated_at: String?
)