package dang.marty.roomatechores.repository


import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Observable

/**
 *   Created by Marty Dang on 2020-01-11
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class Repo {

    private var auth = FirebaseAuth.getInstance()

    fun isUserLoggedIn(): Boolean  {
        if(FirebaseAuth.getInstance().currentUser != null){
            return true
        }
        return false
    }


    fun loggedInSuccessfully(email: String, password: String): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(email, password)
    }

    fun logUserOut() {
        auth.signOut()
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