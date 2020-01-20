package dang.marty.roomatechores.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dang.marty.roomatechores.repository.Repo

class LoginViewModel : ViewModel() {

    private val repo = Repo()

    val loginObservable: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun authenticateUser(email: String, password: String) {
        repo.loggedInSuccessfully(email, password).addOnCompleteListener {
            loginObservable.value = it.isSuccessful
        }
    }
}
