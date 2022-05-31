package com.sviridov.icerockprac.getrequest
import com.sviridov.icerockprac.IKeyValueStorage
import javax.inject.Inject


class GetTokenUseCase
@Inject
constructor(
    private val keyValueStorage: IKeyValueStorage
) {
    operator fun invoke(): String?
    = keyValueStorage.authToken
}