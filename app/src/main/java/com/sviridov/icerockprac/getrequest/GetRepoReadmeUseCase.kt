package com.sviridov.icerockprac.getrequest

import com.sviridov.icerockprac.IAppRepository
import javax.inject.Inject


class GetRepoReadmeUseCase
@Inject
constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(
        owner: String,
        repo: String
    ) : String
    = appRepository.getRepositoryReadme(
        ownerName = owner,
        repositoryName = repo
    )
}