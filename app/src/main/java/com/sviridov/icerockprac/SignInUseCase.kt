package com.sviridov.icerockprac

import com.sviridov.icerockprac.IAppRepository
import javax.inject.Inject


class SignInUseCase
@Inject
constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(token: String)
    = appRepository.signIn(token)
}