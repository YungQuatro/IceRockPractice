package com.sviridov.icerockprac

import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    val login: String
)