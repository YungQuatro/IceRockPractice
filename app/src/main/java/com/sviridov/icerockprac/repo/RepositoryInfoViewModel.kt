package com.sviridov.icerockprac.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sviridov.icerockprac.*
import com.sviridov.icerockprac.getrequest.GetRepoDetailsUseCase
import com.sviridov.icerockprac.getrequest.GetRepoReadmeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RepositoryInfoViewModel
@Inject
constructor(
    private val getRepoDetails: GetRepoDetailsUseCase,
    private val getRepoReadme: GetRepoReadmeUseCase,
    private val replaceReadmeLocalUris: ReplaceReadmeLocalUrisUseCase
) : ViewModel() {
    private val _state: MutableLiveData<State> = MutableLiveData(State.Loading)
    val state: LiveData<State>
        get() = _state

    fun loadInfo(repoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.postValue(State.Loading)

            try {
                getRepoDetails(repoId).let { repoDetails ->

                    val newState = State.Loaded(
                        githubRepo = repoDetails,
                        readmeState = ReadmeState.Loading
                    )

                    _state.postValue(newState)

                    loadReadme(newState)
                }
            } catch (ex: UnauthorizedException) {
                _state.postValue(State.Error("Invalid token."))
            } catch (ex: ServerNotRespondingException) {
                _state.postValue(State.Error("Server not responding..."))
            } catch (ex: ConnectionErrorException) {
                _state.postValue(State.ConnectionError)
            } catch (ex: Exception) {
                _state.postValue(State.Error(ex.message ?: "Undescribed error: ${ex::class.java}"))
            }
        }
    }

    private suspend fun loadReadme(state: State.Loaded) {
        try {
            getRepoReadme(
                owner = state.githubRepo.owner,
                repo = state.githubRepo.name
            ).let { markdown ->
                replaceReadmeLocalUris(
                    owner = state.githubRepo.owner,
                    repo = state.githubRepo.name,
                    readme = markdown
                ).let { processedReadme ->
                    _state.postValue(
                        state.copy(
                            readmeState = ReadmeState.Loaded(processedReadme)
                        )
                    )
                }
            }
        } catch (ex: NotFoundException) {
            _state.postValue(state.copy(readmeState = ReadmeState.Empty))
        } catch (ex: UnauthorizedException) {
            _state.postValue(state.copy(readmeState = ReadmeState.Error("Invalid token.")))
        } catch (ex: ServerNotRespondingException) {
            _state.postValue(state.copy(readmeState = ReadmeState.Error("Server not responding")))
        } catch (ex: ConnectionErrorException) {
            _state.postValue(state.copy(readmeState = ReadmeState.ConnectionError))
        } catch (ex: Exception) {
            _state.postValue(
                state.copy(
                    readmeState = ReadmeState.Error(
                        ex.message ?: "Undescribed error: ${ex::class.java}"
                    )
                )
            )
        }
    }

    sealed interface State {
        object Loading : State
        data class Error(val error: String) : State
        object ConnectionError : State
        data class Loaded(
            val githubRepo: RepoDetails,
            val readmeState: ReadmeState
        ) : State
    }

    sealed interface ReadmeState {
        object Loading : ReadmeState
        object Empty : ReadmeState
        data class Error(val error: String) : ReadmeState
        object ConnectionError : ReadmeState
        data class Loaded(val markdown: String) : ReadmeState
    }
}