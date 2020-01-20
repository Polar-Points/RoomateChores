package dang.marty.roomatechores.repository


import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.Token

/**
 *   Created by Marty Dang on 2020-01-11
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class Repo {

    private var auth = FirebaseAuth.getInstance()

    fun createNewUser(email: String, password: String): Task<AuthResult> {
        return auth.createUserWithEmailAndPassword(email, password)
    }

    fun addNewUserToDB(idToken: String, code: String): Task<Void> {
        val chores = hashMapOf(
            "ChoresList" to listOf("Eat", "sleep", "drink")
        )
        val db = FirebaseFirestore.getInstance()
        return db.document("groups/$code/users/$idToken").set(chores)
    }

    fun getUserToken(): Task<GetTokenResult>? {
        val user = FirebaseAuth.getInstance().currentUser
        return user?.getIdToken(true)
    }

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
        return listOf("Sweep up kitchen", "Wipe counter")
    }
}