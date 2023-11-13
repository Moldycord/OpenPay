package com.danieer.galvez.openpay.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.danieer.galvez.openpay.R
import com.danieer.galvez.openpay.databinding.HomeActivityBinding
import com.danieer.galvez.openpay.presentation.di.factory.ViewModelFactory
import com.danieer.galvez.openpay.presentation.ui.viewmodel.MovieSearchViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    private lateinit var homeBinding: HomeActivityBinding


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MovieSearchViewModel


    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        homeBinding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)


        viewModel = ViewModelProvider(this, viewModelFactory)[MovieSearchViewModel::class.java]
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_profile,
            R.id.navigation_movies,
            R.id.navigation_map,
            R.id.navigation_upload
        ).build()

        homeBinding.bottomNavigation.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp()
    }

}