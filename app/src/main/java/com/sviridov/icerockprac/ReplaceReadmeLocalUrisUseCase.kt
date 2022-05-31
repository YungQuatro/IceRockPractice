package com.sviridov.icerockprac

import javax.inject.Inject


class ReplaceReadmeLocalUrisUseCase
@Inject
constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(owner:String, repo:String, readme: String): String {
        val uris = parseLocalUris(readme)

        var readmeBuilder = readme

        uris.forEach { localUri ->
            val globalUri = appRepository.getImageUrl(owner, repo, localUri.uri)

            readmeBuilder = readmeBuilder.replace(localUri.entry, localUri.entry.replace(localUri.uri, globalUri))
        }

        return readmeBuilder
    }
}