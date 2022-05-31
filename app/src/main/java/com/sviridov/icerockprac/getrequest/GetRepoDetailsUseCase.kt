package com.sviridov.icerockprac.getrequest

import com.sviridov.icerockprac.repo.RepoDetails
import com.sviridov.icerockprac.IAppRepository
import javax.inject.Inject


class GetRepoDetailsUseCase
@Inject
constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(repoId: String) : RepoDetails
    = appRepository.getRepository(repoId)
}