package com.danieer.galvez.openpay.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.danieer.galvez.openpay.R
import com.danieer.galvez.openpay.data.entities.Movie
import com.danieer.galvez.openpay.databinding.HomeActivityBinding
import com.danieer.galvez.openpay.presentation.di.factory.ViewModelFactory
import com.danieer.galvez.openpay.presentation.mappers.MoviesAdapter
import com.danieer.galvez.openpay.presentation.ui.viewmodel.MovieSearchViewModel
import dagger.android.AndroidInjection
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), MoviesAdapter.ClickListener {

    private lateinit var homeBinding: HomeActivityBinding


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: MovieSearchViewModel

    private var moviesAdapter = MoviesAdapter(this)

    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        homeBinding = HomeActivityBinding.inflate(layoutInflater)
        setContentView(homeBinding.root)


        viewModel = ViewModelProvider(this, viewModelFactory)[MovieSearchViewModel::class.java]
        setupViews()
        setupObservers()
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

        //  setupActionBarWithNavController(navController, appBarConfiguration)
        homeBinding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setupViews() {/* homeBinding.run {
             recyclerViewMovies.apply {
                 layoutManager = LinearLayoutManager(this@HomeActivity)
                 adapter = moviesAdapter
             }
             editTextSearch.addTextChangedListener {
                 Executors.newSingleThreadScheduledExecutor().schedule({
                     viewModel.getMovieByName(it.toString())
                 }, 2, TimeUnit.SECONDS)
             }
         } */
    }

    private fun setupObservers() {
        viewModel.moviesData.observe(this) {
            moviesAdapter.setMovieList(it.movies)
        }
    }


    private fun goToDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movie)
        startActivity(intent)
    }

    override fun onElementClicked(movie: Movie) {
        goToDetails(movie)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp()
    }


}