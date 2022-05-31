package com.sviridov.icerockprac

import com.sviridov.icerockprac.repo.Repo
import com.sviridov.icerockprac.repo.RepositoryShortResponse

fun RepositoryShortResponse.toRepo()
= Repo(
    name = this.name,
    description = this.description ?: "",
    language = this.language ?: "",
    color = null
)