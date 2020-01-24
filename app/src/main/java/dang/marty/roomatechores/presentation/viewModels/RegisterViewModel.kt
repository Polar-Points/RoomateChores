package dang.marty.roomatechores.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dang.marty.roomatechores.repository.Repo

/**
 *   Created by Marty Dang on 2020-01-18
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class RegisterViewModel: ViewModel() {

    private val repo = Repo()
    val registerObservable: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

    fun registerNewUser(name: String, code: String, email: String, password: String) {
        repo.createNewUser(email, password).addOnCompleteListener { createNewUser ->
            if(createNewUser.isSuccessful){
                repo.getUserToken()?.addOnCompleteListener {
                    val token = it.result?.token
                    if(token != null) {
                        repo.addNewUserToDB(token, code).addOnCompleteListener{ addToDB ->
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
