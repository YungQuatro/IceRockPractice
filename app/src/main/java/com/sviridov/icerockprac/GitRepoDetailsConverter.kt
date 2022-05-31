package com.sviridov.icerockprac

import com.sviridov.icerockprac.repo.RepoDetails
import com.sviridov.icerockprac.repo.RepositoryDetailsResponse

fun RepositoryDetailsResponse.toRepoDetails()
= RepoDetails(
    url = this.htmlUrl,
    name = this.name,
    owner = this.owner.login,
    license = this.license?.name ?: "",
    stargazersCount = this.stargazersCount,
    forksCount = this.forksCount,
    watchersCount = this.watchersCount
)