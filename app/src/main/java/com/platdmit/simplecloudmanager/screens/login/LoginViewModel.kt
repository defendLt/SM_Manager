package com.platdmit.simplecloudmanager.screens.login

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.SavedStateHandle
import com.platdmit.domain.enums.ErrorType
import com.platdmit.domain.models.UserAccount
import com.platdmit.domain.usecase.LoginUseCase
import com.platdmit.simplecloudmanager.base.BaseViewModel
import com.platdmit.simplecloudmanager.utilities.NetworkHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel
@ViewModelInject
constructor(
        private val loginUseCase: LoginUseCase,
        private val networkHelper: NetworkHelper,
        @Assisted private val savedStateHandle: SavedStateHandle
) : BaseViewModel<LoginState>() {

    val loginStateLiveData = LiveDataReactiveStreams.fromPublisher(stateProvider)
    private lateinit var userAccount: UserAccount

    init {
        stateProvider.onNext(LoginState.Loading)
        initAccountData()
    }

    fun setStateIntent(stateIntent: StateIntent){
        when(stateIntent){
            is StateIntent.NewAccount -> {
                addNewAccount(stateIntent.login, stateIntent.pass)
            }
            is StateIntent.NewAccountPin -> {
                addNewAccountPin(stateIntent.pin)
            }
            is StateIntent.CheckAccountPin -> {
                checkAccountPin(stateIntent.pin)
            }
            is StateIntent.RepeatConnect -> {
                initAccountData()
            }
            is StateIntent.OnDemoMode -> {
                onDemoAccount()
            }
            is StateIntent.ResetScreen -> {
                stateProvider.onNext(LoginState.ActiveUserNo)
            }
        }
    }

    private fun initAccountData(){
        if(networkHelper.getNetworkStatus()) {
            compositeDisposable.add(
                    loginUseCase.getActiveAccount()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                if (it.pin.isEmpty()) {
                                    stateProvider.onNext(LoginState.UserNeedPin)
                                } else {
                                    userAccount = it
                                    stateProvider.onNext(LoginState.ActiveUserYes)
                                }
                            }, {
                                stateProvider.onNext(LoginState.ActiveUserNo)
                            })
            )
        } else {
            stateProvider.onNext(
                    LoginState.Error(ErrorType.FALL_CONNECT)
            )
        }
    }

    private fun addNewAccount(login: String, pass: String) {
        compositeDisposable.add(
                loginUseCase.addAccount(login, pass)
                        .subscribe({
                            userAccount = it
                            stateProvider.onNext(LoginState.UserNeedPin)
                        }, {
                            stateProvider.onNext(LoginState.AuthInvalid)
                        })
        )
    }

    private fun addNewAccountPin(pin: String) {
        userAccount.pin = pin
        compositeDisposable.add(
                loginUseCase.addAccountPin(userAccount)
                        .subscribe ({
                            stateProvider.onNext(LoginState.Success)
                        }, {
                            stateProvider.onNext(LoginState.Error(ErrorType.FALL_AUTH))
                        })
        )
    }

    private fun checkAccountPin(pin: String?) {
        compositeDisposable.add(
                loginUseCase.checkAccountPin(pin, userAccount)
                        .observeOn(Schedulers.newThread())
                        .subscribe({
                            successAuth()
                        }, {
                            stateProvider.onNext(LoginState.PinInvalid)
                        })
        )
    }

    private fun successAuth() {
        compositeDisposable.add(
                loginUseCase.successAuthAccount()
                        .observeOn(Schedulers.newThread())
                        .onErrorComplete()
                        .subscribe {
                            stateProvider.onNext(LoginState.Success)
                        }
        )
    }

    private fun onDemoAccount() {
        compositeDisposable.add(
                loginUseCase.startDemoAccount()
                        .subscribe {
                            stateProvider.onNext(LoginState.OnDemo)
                        }
        )
    }

    sealed class StateIntent {
        data class NewAccount(val login: String, val pass: String) : StateIntent()
        data class NewAccountPin(val pin: String) : StateIntent()
        data class CheckAccountPin(val pin: String?) : StateIntent()
        object RepeatConnect: StateIntent()
        object OnDemoMode : StateIntent()
        object ResetScreen : StateIntent()
    }
}