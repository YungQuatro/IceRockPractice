package com.sviridov.icerockprac

import kotlinx.serialization.Serializable

@Serializable
data class ErrorBody(
    val message: String
)