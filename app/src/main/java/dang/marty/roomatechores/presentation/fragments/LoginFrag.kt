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

import dang.marty.roomatechores.R
import dang.marty.roomatechores.presentation.viewModels.LoginViewModel

class LoginFrag : Fragment(){

    private lateinit var viewModel: LoginViewModel

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    private val loginButtonOnClick = View.OnClickListener {
        viewModel.authenticateUser(emailField.text.toString(), passwordField.text.toString())
    }

    private val registerButtonOnClick = View.OnClickListener {
        findNavController().navigate(R.id.registerFrag)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.login_fragment, container, false)
        setUpView(view)
        return view
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.loginObservable.observe(this, Observer<Boolean> { userSuccessfullyloggedIn ->
            if (userSuccessfullyloggedIn){
                findNavController().navigate(R.id.my_chores_tab)
            } else {
               showUnsuccessfulLoginMessage()
            }
        })
    }

    private fun setUpView(view: View) {
        emailField  = view.findViewById(R.id.email_field)
        passwordField = view.findViewById(R.id.password_field)
        loginButton = view.findViewById(R.id.login_button)
        registerButton = view.findViewById(R.id.register_button)
        registerButton.setOnClickListener(registerButtonOnClick)
        loginButton.setOnClickListener(loginButtonOnClick)
        emailField.setText("martydang1@gmail.com")
        passwordField.setText("Bottomline123")
    }

    private fun showUnsuccessfulLoginMessage() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Sign in unsuccessful")
        builder.setMessage("Your credentials are incorrect or you don't have a valid account")
        builder.create().show()
    }
}
