package dang.marty.roomatechores

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import dang.marty.roomatechores.viewModels.MainActivityViewModel
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import  dang.marty.roomatechores.viewModels.MainActivityViewModel.AuthenticationState

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViewModel()
        setUpNavigation()
        Timber.plant(Timber.DebugTree())

        mPager = findViewById(R.id.view_pager)
        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager,2)
        mPager.adapter = pagerAdapter
    }

    override fun onStart() {
        super.onStart()
        viewModel.authenticationState.observe(this, Observer { authenticationState ->
            when (authenticationState) {
                AuthenticationState.UNAUTHENTICATED -> initializedUnAuthenticatedState()
                AuthenticationState.AUTHENTICATED -> initializeAuthenticatedState()
                else -> {}
            }
        })
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager.currentItem = mPager.currentItem - 1
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProviders.of(this)[MainActivityViewModel::class.java]
    }

    private fun setUpNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
        NavigationUI.setupWithNavController(bottom_navigation, navController)
    }

    private fun initializeAuthenticatedState() {
        findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.VISIBLE
        navController.navigate(R.id.my_chores_tab)
    }

    private fun initializedUnAuthenticatedState() {
        findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility = View.GONE
        //navController.navigate(R.id.login_tab)
        navController.navigate(R.id.login_tab)
    }
}
