package com.sviridov.icerockprac.navigate

import com.sviridov.icerockprac.CoreDestinations
import com.sviridov.icerockprac.CoreNavProvider
import javax.inject.Inject


class NavigateToRepositoriesListUseCase
@Inject
constructor(
    private val coreNavProvider: CoreNavProvider
) {
    operator fun invoke()
    = coreNavProvider.requestNavigate(CoreDestinations.RepositoriesList)
}