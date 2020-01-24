package dang.marty.roomatechores

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import dang.marty.roomatechores.presentation.viewModels.MainActivityViewModel
import dang.marty.roomatechores.presentation.viewModels.MainActivityViewModel.AuthenticationState
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        setUpNavigation()
        setSupportActionBar(findViewById(R.id.toolbar))
        Timber.plant(Timber.DebugTree())
    }

    override fun onStart() {
        super.onStart()
        isUserAuthenticated()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.log_out -> {
                viewModel.logUserOut()
                initializedUnAuthenticatedState()
            }
        }
        return true
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
    }

    private fun isUserAuthenticated() {
        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                AuthenticationState.UNAUTHENTICATED -> initializedUnAuthenticatedState()
                AuthenticationState.AUTHENTICATED -> initializeAuthenticatedState()
                else -> {}
            }
        })
    }

    private fun setUpNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_navigation, navController)
    }

    private fun initializeAuthenticatedState() {
        findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
//        navController.navigate(R.id.my_chores_tab)
    }

    private fun initializedUnAuthenticatedState() {
       // findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
//        navController.navigate(R.id.loginFrag)
        navController.navigate(R.id.my_chores_tab)

    }
}
