package com.sviridov.icerockprac.repo

data class RepoDetails (
    val url: String,
    val owner: String,
    val name: String,
    val license: String,
    val stargazersCount: Int,
    val forksCount: Int,
    val watchersCount: Int,
)