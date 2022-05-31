package com.sviridov.icerockprac.authentication

import android.util.Base64
import com.sviridov.icerockprac.*
import com.sviridov.icerockprac.getrequest.GetAuthHeaderUseCase
import com.sviridov.icerockprac.repo.Repo
import com.sviridov.icerockprac.repo.RepoDetails

class AppRepository(
    private val gitHubApi: IGitHubApi,
    private val getAuthHeader: GetAuthHeaderUseCase,
    private val keyValueStorage: IKeyValueStorage,
    private val colorsRepository: ILanguageColorsRepository
) : IAppRepository {
    override suspend fun getRepositories(): List<Repo>
    = gitRequestWrapper {
        gitHubApi.getRepositories(getAuthHeader())
    }.map { repoResponse ->
        repoResponse.toRepo().let { repo ->
            if(repo.language.isEmpty()) return@let repo
            repo.copy(color = colorsRepository.getLangColor(repo.language))
        }
    }

    override suspend fun getRepository(repoId: String): RepoDetails
    = gitRequestWrapper {
        gitHubApi.getRepoDetails(
            token = getAuthHeader(),
            owner = keyValueStorage.userName ?: "",
            repo = repoId
        )
    }.toRepoDetails()

    override suspend fun getRepositoryReadme(
        ownerName: String,
        repositoryName: String,
        branchName: String
    ): String {
        return gitRequestWrapper {
            if(branchName.isEmpty())
                gitHubApi.getReadme(
                    token = getAuthHeader(),
                    owner = ownerName,
                    repo = repositoryName
                )
            else
                gitHubApi.getReadme(
                    token = getAuthHeader(),
                    owner = ownerName,
                    repo = repositoryName,
                    branch = branchName
                )
        }.let {
            Base64.decode(it.content, Base64.DEFAULT).decodeToString()
        }
    }

    override suspend fun signIn(token: String): UserInfo
    = gitRequestWrapper {
        gitHubApi.getUserInfo("token $token")
    }.toUserInfo().also {
        keyValueStorage.authToken = token
        keyValueStorage.userName = it.login
    }

    override fun logout() {
        keyValueStorage.authToken = null
        keyValueStorage.userName = null
    }

    override suspend fun getImageUrl(owner: String, repo: String, path: String): String
    = gitRequestWrapper {
        gitHubApi.getContent(
            token = getAuthHeader(),
            owner = owner,
            repo = repo,
            path = path
        )
    }.downloadUrl
}