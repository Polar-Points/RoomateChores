package dang.marty.roomatechores.presentation.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dang.marty.roomatechores.repository.Repo

/**
 *   Created by Marty Dang on 2019-12-31
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class MyChoresViewModel: ViewModel() {

    private val repo = Repo()

    val choresObservable: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }


    // read from database only when they restart the app, save it locally if they go from it

     fun getChoresListFromLocal() {
        choresObservable.postValue(repo.getChoresSavedLocally())
    }

    fun getChoreListFromRemote() {
        choresObservable.postValue(repo.getChoresSavedRemotely())
    }
}