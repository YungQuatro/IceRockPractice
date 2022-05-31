package com.sviridov.icerockprac.getrequest

import com.sviridov.icerockprac.IKeyValueStorage
import javax.inject.Inject


class GetAuthHeaderUseCase
@Inject
constructor(
    private val keyValueStorage: IKeyValueStorage
) {
    operator fun invoke() : String
    = "token ${keyValueStorage.authToken}"
}