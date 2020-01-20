package dang.marty.roomatechores.presentation.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dang.marty.roomatechores.R
import dang.marty.roomatechores.presentation.viewModels.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_register.*
import timber.log.Timber
import kotlin.math.log

/**
 *   Created by Marty Dang on 2020-01-17
 *   Copyright @ 2019 Dang, Marty. All rights reserved.
 */
class RegisterFrag: Fragment() {

    private lateinit var viewModel: RegisterViewModel

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var codeField: TextView
    private lateinit var nameField: EditText


    private lateinit var loginButton: Button
    private lateinit var registerButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpViewModel()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_register, container, false)

        emailField = view.findViewById(R.id.email_field)
        passwordField = view.findViewById(R.id.password_field)
        nameField = view.findViewById(R.id.name_field)
        codeField = view.findViewById(R.id.code_display)
        emailField.setText("martydang2@gmail.com")
        passwordField.setText("Bottomline12345")
        codeField.text = "5"

        loginButton = view.findViewById(R.id.login_now_button)
        loginButton.setOnClickListener {
            findNavController().navigate(R.id.loginFrag)
        }
        registerButton = view.findViewById(R.id.register_button)
        registerButton.setOnClickListener {
            viewModel.registerNewUser(
                name_field.text.toString(), codeField.text.toString(),
                emailField.text.toString(), passwordField.text.toString())
        }
        return view
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
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

}