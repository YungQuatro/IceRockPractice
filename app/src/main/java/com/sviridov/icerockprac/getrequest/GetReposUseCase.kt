package com.sviridov.icerockprac.getrequest

import com.sviridov.icerockprac.repo.Repo
import com.sviridov.icerockprac.IAppRepository
import javax.inject.Inject


class GetReposUseCase
@Inject
constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke() : List<Repo>
    = appRepository.getRepositories()
}