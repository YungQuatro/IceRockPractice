package com.sviridov.icerockprac

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContentResponse(
    @SerialName("download_url")
    val downloadUrl: String
)