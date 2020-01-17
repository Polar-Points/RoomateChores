package dang.marty.roomatechores.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import dang.marty.roomatechores.R
import dang.marty.roomatechores.ui.LoginViewModel
import timber.log.Timber

class LoginFrag : Fragment() {


    private lateinit var auth: FirebaseAuth

    private lateinit var codeField: EditText
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var login: Button
    private lateinit var logout: Button
    private lateinit var signup: Button
    private lateinit var getUserInfo: Button


    companion object {
        fun newInstance() = LoginFrag()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.login_fragment, container, false)

        codeField = view.findViewById(R.id.code)
        emailField  = view.findViewById(R.id.email)
        passwordField = view.findViewById(R.id.password)
        login = view.findViewById(R.id.login_button)
        logout = view.findViewById(R.id.logout)
        signup = view.findViewById(R.id.register_button)
        getUserInfo = view.findViewById(R.id.get_user_info)

        login.setOnClickListener{
            signIn(emailField.text.toString(), passwordField.text.toString())
        }

        logout.setOnClickListener{
            logout()
        }

        signup.setOnClickListener{
            Timber.d("fields %s %s", emailField.text.toString(), passwordField.text.toString())
            createUserWithEmailAndPassword(emailField.text.toString(), passwordField.text.toString())
        }

        getUserInfo.setOnClickListener{
            getUserInfo()
        }

        codeField.setText("12345")
        emailField.setText("martydang1@gmail.com")
        passwordField.setText("Bottomline123")


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
    }

    fun createUserWithEmailAndPassword(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
            if (it.isSuccessful){
                Snackbar.make(view!!, "Registration successsful", Snackbar.LENGTH_LONG)
                addNewUserToDb()
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

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){
                Snackbar.make(view!!, "Login successsful", Snackbar.LENGTH_LONG)
            } else {
                Snackbar.make(view!!, "Login Unsuccessful " + it.exception, Snackbar.LENGTH_LONG)
            }
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
    }

    fun getUserInfo(){
        val user = FirebaseAuth.getInstance().currentUser
        user?.let {firebaseUser ->
            firebaseUser.getIdToken(true).addOnCompleteListener {
                if(it.isSuccessful){
                    var idToken = it.result?.token

                    var groupId = codeField.text.toString()

                    val db = FirebaseFirestore.getInstance()

                    db.document("groups/$groupId/users/$idToken").get().addOnSuccessListener {
                        Timber.d("CHORES FOR %s %s",idToken,it.getDate("ChoreList"))
                    }

                } else {
                    Snackbar.make(view!!, "Token not valid " + it.exception, Snackbar.LENGTH_LONG)
                }
            }
        }
    }
}
