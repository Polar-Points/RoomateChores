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
import dang.marty.roomatechores.Utils
import dang.marty.roomatechores.presentation.viewModels.LoginViewModel
import timber.log.Timber

class LoginFrag : Fragment(){

    private lateinit var viewModel: LoginViewModel

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText

    private lateinit var mainButton: Button
    private lateinit var secondaryButton: Button

    private val mainButtonOnClick = View.OnClickListener {
        viewModel.determineMainButtonAction(emailField.text.toString(), passwordField.text.toString())
    }

    private val secondaryButtonOnClick = View.OnClickListener {
        viewModel.determineSecondaryButtonAction()
//        findNavController().navigate(R.id.registerFrag)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.login_fragment, container, false)
        setUpView(view)
        Timber.d("DFSFDSF " + Utils.generateGroupCode())
        return view
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        viewModel.onBoardFragObservable.observe(this, Observer<String> {
            if(it == "Login"){
                mainButton.text = "Login"
                secondaryButton.text = "Create a new account"
            } else {
                mainButton.text = "Register"
                secondaryButton.text = "Login to account"
            }
        })
        viewModel.loginObservable.observe(this, Observer<Boolean> { userSuccessfullyloggedIn ->
            if (userSuccessfullyloggedIn){
                findNavController().navigate(R.id.my_chores_tab)
            } else {
               showUnsuccessfulLoginMessage()
            }
        })
        viewModel.registerObservable.observe(this, Observer<Boolean>{ registerSuccess ->
            if(registerSuccess){
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Account created successfully!")
                builder.setMessage("Please login with your new creds")
                builder.create().show()
            } else {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Unable to create account")
                builder.setMessage("Something went wrong")
                builder.create().show()
            }
        })
    }

    private fun setUpView(view: View) {
        emailField  = view.findViewById(R.id.email_field)
        passwordField = view.findViewById(R.id.password_field)
        mainButton = view.findViewById(R.id.main_button)
        mainButton.setOnClickListener(mainButtonOnClick)
        secondaryButton = view.findViewById(R.id.secondary_button)
        secondaryButton.setOnClickListener(secondaryButtonOnClick)
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
