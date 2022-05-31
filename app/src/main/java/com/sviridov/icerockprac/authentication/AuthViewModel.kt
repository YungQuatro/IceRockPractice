package com.sviridov.icerockprac.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sviridov.icerockprac.*
import com.sviridov.icerockprac.getrequest.GetTokenUseCase
import com.sviridov.icerockprac.navigate.NavigateToRepositoriesListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel
@Inject
constructor(
    private val getToken: GetTokenUseCase,
    private val signIn: SignInUseCase,
    private val navigateToRepositoriesList: NavigateToRepositoriesListUseCase
) : ViewModel() {
    val token: MutableLiveData<String> = MutableLiveData("")

    private val _state: MutableLiveData<State> = MutableLiveData(State.Loading)
    val state: LiveData<State>
        get() = _state

    private val _actions: Channel<Action> = Channel()
    val actions: Flow<Action>
        get() = _actions.receiveAsFlow()

    init {
        //Try to auth from local storage
        viewModelScope.launch {
            try {
                val token = getToken()
                if(token == null) {
                    _state.postValue(State.Idle)
                    return@launch
                }

                signIn(token)
                navigateToRepositoriesList()
            } catch (ex: ServerNotRespondingException) {
                _state.postValue(State.Idle)
                _actions.send(Action.ShowError("Server not responding..."))
            } catch (ex: ConnectionErrorException) {
                _state.postValue(State.Idle)
                _actions.send(Action.ShowError("Connection error."))
            } catch (ex: UnauthorizedException) {
                _state.postValue(State.InvalidInput(ex.message))
            } catch (ex: Exception) {
                _state.postValue(State.Idle)
                _actions.send(
                    Action.ShowError(
                        ex.message ?: "Undescribed error: ${ex::class.java}"
                    )
                )
            }
        }
    }

    fun onSignButtonPressed() {
        viewModelScope.launch {
            _state.postValue(State.Loading)
            try {
                if(token.value.isNullOrEmpty()) {
                    _state.postValue(State.InvalidInput("Enter the token."))
                    return@launch
                }

                signIn(token.value!!)
                navigateToRepositoriesList()
            } catch (ex: UnauthorizedException) {
                _state.postValue(State.InvalidInput(ex.message))
            } catch (ex: ServerNotRespondingException) {
                _state.postValue(State.Idle)
                _actions.send(Action.ShowError("Server not responding..."))
            } catch (ex: ConnectionErrorException) {
                _state.postValue(State.Idle)
                _actions.send(Action.ShowError("Connection error."))
            } catch (ex: Exception) {
                _state.postValue(State.Idle)
                _actions.send(
                    Action.ShowError(
                        ex.message ?: "Undescribed error: ${ex::class.java}"
                    )
                )
            }
        }
    }

    sealed interface State {
        object Idle : State
        object Loading : State
        data class InvalidInput(val reason: String) : State
    }

    sealed interface Action {
        data class ShowError(val message: String) : Action
        object RouteToMain : Action
    }
}