package com.sviridov.icerockprac.repo

import com.sviridov.icerockprac.License
import com.sviridov.icerockprac.UserInfoResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoryDetailsResponse(
    @SerialName("html_url")
    val htmlUrl: String,
    val name: String,
    val owner: UserInfoResponse,
    val license: License?,
    @SerialName("stargazers_count")
    val stargazersCount: Int,
    @SerialName("forks_count")
    val forksCount: Int,
    @SerialName("watchers_count")
    val watchersCount: Int,
)
