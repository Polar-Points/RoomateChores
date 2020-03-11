package dang.marty.roomatechores.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dang.marty.roomatechores.repository.Repo

class LoginViewModel : ViewModel() {

    private val repo = Repo()

    val onBoardFragObservable: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    val loginObservable: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
    val registerObservable: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    var status = "Login"

    fun determineMainButtonAction(email: String, password: String) {
        if(status == "Login"){
            authenticateUser(email, password)
        } else {
            registerNewUser(email, password)
        }
    }

    fun determineSecondaryButtonAction() {
        if(status == "Login"){
            status = "Register"
            onBoardFragObservable.postValue("Register")
        } else {
            status = "Login"
            onBoardFragObservable.postValue("Login")
        }
    }

    fun authenticateUser(email: String, password: String) {
        repo.loggedInSuccessfully(email, password).addOnCompleteListener {
            loginObservable.value = it.isSuccessful
        }
    }

    fun registerNewUser(email: String, password: String) {
        repo.createNewUser(email, password).addOnCompleteListener { createNewUser ->
            if(createNewUser.isSuccessful){
                repo.getUserToken()?.addOnCompleteListener {
                    val token = it.result?.token
                    if(token != null) {
                        repo.addNewUserToDB(token, "1234").addOnCompleteListener{ addToDB ->
                            registerObservable.value = addToDB.isSuccessful
                        }
                    }
                }
            } else {
                registerObservable.value = false
            }
        }
    }
}
