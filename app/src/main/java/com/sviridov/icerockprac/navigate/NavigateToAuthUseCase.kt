package com.sviridov.icerockprac.navigate

import com.sviridov.icerockprac.CoreDestinations
import com.sviridov.icerockprac.CoreNavProvider
import javax.inject.Inject


class NavigateToAuthUseCase
@Inject
constructor(
    private val coreNavProvider: CoreNavProvider
) {
    operator fun invoke()
    = coreNavProvider.requestNavigate(CoreDestinations.Authentication)
}