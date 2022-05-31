package com.sviridov.icerockprac

import kotlinx.serialization.Serializable

@Serializable
data class ReadmeResponse (
    val content: String
)