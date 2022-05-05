package com.farm.ezy.activity

import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.farm.ezy.R
import com.farm.ezy.core.utils.currentNavigationFragment
import com.farm.ezy.databinding.ActivityMainBinding
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {


    private val binding: ActivityMainBinding by viewBinding()
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)
        binding.apply {
            setSupportActionBar(binding.mainToolbar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.navController
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    com.farm.ezy.login.R.id.logInFragment,
                    com.farm.ezy.home.R.id.homeFragment,
                    com.farm.ezy.profile.R.id.fragmentProfile
                )
            )
            bottomNavigation.setupWithNavController(navController)
            bottomNavigation.setOnItemReselectedListener { }
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
        onChangeDestination()
    }

    private fun onChangeDestination() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            setExitTransition()
            when (destination.id) {
                com.farm.ezy.home.R.id.homeFragment, com.farm.ezy.profile.R.id.fragmentProfile -> {
                    binding.bottomLayout.isVisible = true
                }
                else -> {
                    binding.bottomLayout.isVisible = false
                }
            }

        }
    }

    private fun getCurrentFragment(): Fragment? =
        supportFragmentManager.currentNavigationFragment


    private fun setExitTransition() {
        getCurrentFragment()?.apply {
            exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
            reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}