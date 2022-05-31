package com.sviridov.icerockprac

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.sviridov.icerockprac.authentication.AppRepository
import com.sviridov.icerockprac.getrequest.GetAuthHeaderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@ExperimentalSerializationApi
@InstallIn(SingletonComponent::class)
@Module
class ModuleCore {

    @Singleton
    @Provides
    fun provideCoreNavProvider(): CoreNavProvider = CoreNavProvider()

    private val json = Json { ignoreUnknownKeys = true }
    private val mediaType = MediaType.get("application/json; charset=utf-8")
    @Singleton
    @Provides
    fun provideGitApi(): IGitHubApi
    = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(json.asConverterFactory(mediaType))
        .build()
        .create(IGitHubApi::class.java)

    @Singleton
    @Provides
    fun provideKeyValueStorage(@ApplicationContext context: Context): IKeyValueStorage = KeyValueStorage(context)

    @Singleton
    @Provides
    fun provideColorsRepository(@ApplicationContext context: Context): ILanguageColorsRepository = LanguageColorsRepository(context)

    @Singleton
    @Provides
    fun provideAppRepository(
        gitHubApi: IGitHubApi,
        getAuthHeaderUseCase: GetAuthHeaderUseCase,
        keyValueStorage: IKeyValueStorage,
        colorsRepository: ILanguageColorsRepository
    ): IAppRepository
    = AppRepository(
        gitHubApi = gitHubApi,
        getAuthHeader = getAuthHeaderUseCase,
        keyValueStorage = keyValueStorage,
        colorsRepository = colorsRepository
    )
}