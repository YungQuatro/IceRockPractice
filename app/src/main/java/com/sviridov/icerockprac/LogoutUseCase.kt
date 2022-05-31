package com.sviridov.icerockprac

import com.sviridov.icerockprac.IAppRepository
import javax.inject.Inject


class LogoutUseCase
@Inject
constructor(
    private val appRepository: IAppRepository
) {
    operator fun invoke()
    = appRepository.logout()
}