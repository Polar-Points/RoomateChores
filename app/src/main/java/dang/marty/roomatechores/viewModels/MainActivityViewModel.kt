package dang.marty.roomatechores.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dang.marty.roomatechores.repository.Repo
import timber.log.Timber

/**
 *   Created by Marty Dang on 2020-01-15
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class MainActivityViewModel: ViewModel() {

    private val repo = Repo()

    enum class AuthenticationState {
        UNAUTHENTICATED,
        AUTHENTICATED,
    }

    val authenticationState = MutableLiveData<AuthenticationState>()

    init {
        isUserLoggedIn()
    }

    private fun isUserLoggedIn() {
        Timber.d("isUserLoggedIn %s", repo.isUserLoggedIn())
        if (repo.isUserLoggedIn()) {
            authenticationState.value = AuthenticationState.AUTHENTICATED
        } else {
            authenticationState.value = AuthenticationState.UNAUTHENTICATED
        }
    }
}