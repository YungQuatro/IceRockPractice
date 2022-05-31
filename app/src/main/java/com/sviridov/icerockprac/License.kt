package com.sviridov.icerockprac

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class License(
    @SerialName("spdx_id")
    val name: String?
)