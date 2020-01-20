package dang.marty.roomatechores.presentation.fragments

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import dang.marty.roomatechores.R
import dang.marty.roomatechores.presentation.viewModels.LoginViewModel
import dang.marty.roomatechores.repository.Repo
import timber.log.Timber

class LoginFrag : Fragment() {

    private lateinit var viewModel: LoginViewModel

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.login_fragment, container, false)

        emailField  = view.findViewById(R.id.email_field)
        passwordField = view.findViewById(R.id.password_field)
        loginButton = view.findViewById(R.id.login_button)
        registerButton = view.findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            findNavController().navigate(R.id.registerFrag)
        }

        loginButton.setOnClickListener{
            viewModel.authenticateUser(emailField.text.toString(), passwordField.text.toString())
        }

        emailField.setText("martydang1@gmail.com")
        passwordField.setText("Bottomline123")

        return view
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
//        if (auth.currentUser == null) {
//            FirebaseAuth.getInstance().signOut()
//            findNavController().navigate(R.id.loginFrag)
//        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.loginObservable.observe(this, Observer<Boolean> { userSuccessfullyloggedIn ->
            if (userSuccessfullyloggedIn){
                findNavController().navigate(R.id.my_chores_tab)
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Sign in unsuccessful")
                builder.setMessage("Your credentials are incorrect or you don't have a valid account")
                builder.create().show()
            }
        })
    }
}
