package com.sviridov.icerockprac

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable()
data class ContentLinkResponse(
    @SerialName("download_link")
    val downloadLink: String
)