package dang.marty.roomatechores.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dang.marty.roomatechores.R
import timber.log.Timber
import kotlin.math.log

/**
 *   Created by Marty Dang on 2020-01-17
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class RegisterFrag: Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var codeField: EditText

    private lateinit var loginButton: Button
    private lateinit var registerButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_register, container, false)

        emailField = view.findViewById(R.id.email_field)
        passwordField = view.findViewById(R.id.password_field)

        loginButton = view.findViewById(R.id.login_now_button)
        loginButton.setOnClickListener {
            findNavController().navigate(R.id.loginFrag)
        }
        registerButton = view.findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            createUserWithEmailAndPassword(emailField.text.toString(), passwordField.text.toString())
        }
        return view
    }

    fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful){
                //addNewUserToDb()
            } else {
                Snackbar.make(view!!, "Registration Unsuccessful " + it.exception, Snackbar.LENGTH_LONG)
            }
        }
    }

    fun addNewUserToDb() {
        val user = FirebaseAuth.getInstance().currentUser
        user?.let { firebaseUser ->
            firebaseUser.getIdToken(true).addOnCompleteListener {
                if (it.isSuccessful){
                    var idToken = it.result?.token
                    var groupId = codeField.text.toString()

                    val chores = hashMapOf(
                        "ChoresList" to listOf("Eat", "sleep", "drink")
                    )

                    val db = FirebaseFirestore.getInstance()
                    db.document("groups/$groupId/users/$idToken").set(chores)
                        .addOnSuccessListener {
                            Timber.d("Added new data successfully")
                        }
                        .addOnFailureListener {
                            Timber.d("unsucuesful data addition ")
                        }

                }
            }
        }

    }

}