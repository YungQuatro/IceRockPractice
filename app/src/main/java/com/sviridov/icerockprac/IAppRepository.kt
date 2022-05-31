package com.sviridov.icerockprac

import com.sviridov.icerockprac.repo.Repo
import com.sviridov.icerockprac.repo.RepoDetails

interface IAppRepository {
        suspend fun getRepositories(): List<Repo>

        suspend fun getRepository(repoId: String): RepoDetails

        suspend fun getRepositoryReadme(ownerName: String, repositoryName: String, branchName: String = ""): String

        suspend fun signIn(token: String): UserInfo

        fun logout()

        suspend fun getImageUrl(owner: String, repo: String, path: String): String
}