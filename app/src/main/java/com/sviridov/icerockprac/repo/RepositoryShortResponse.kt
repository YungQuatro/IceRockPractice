package com.sviridov.icerockprac.repo

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryShortResponse(
    val name: String,
    val description: String?,
    val language: String?
)