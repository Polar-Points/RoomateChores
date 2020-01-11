package dang.marty.roomatechores.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *   Created by Marty Dang on 2019-12-31
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class MyChoresViewModel: ViewModel() {

    val choresObservable: MutableLiveData<List<String>> by lazy {
        MutableLiveData<List<String>>()
    }

     fun getChoresList() {
        choresObservable.postValue(listOf("Sweep up kitchen", "Wipe counter"))
    }
}