package dang.marty.roomatechores.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber

/**
 *   Created by Marty Dang on 2020-01-11
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class Repo {

    fun isUserLoggedIn(): Boolean  {
        if(FirebaseAuth.getInstance().currentUser != null){
            return true
        }
        return false
    }

    fun logUserIn() {

    }

    fun logUserOut() {

    }

    fun getChoresSavedLocally(): List<String> {
        return listOf("Sweep up kitchen", "Wipe counter")
    }

    fun getChoresSavedRemotely(): List<String> {

//        val db = FirebaseFirestore.getInstance()
//        db.collection("groups/group2/users").get().addOnSuccessListener {
//             it.documents.forEach {
//                Timber.d("YOO %s", it.id)
//            }
//        }

        return listOf("Sweep up kitchen", "Wipe counter")
    }
}