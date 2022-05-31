package com.sviridov.icerockprac.navigate

import com.sviridov.icerockprac.CoreDestinations
import com.sviridov.icerockprac.CoreNavProvider
import javax.inject.Inject


class NavigateToDetailsUseCase
@Inject
constructor(
    private val coreNavProvider: CoreNavProvider
) {
    operator fun invoke(repoId: String) {
        coreNavProvider.requestNavigate(CoreDestinations.RepositoryDetails(repoId))
    }
}