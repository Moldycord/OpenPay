package com.danieer.galvez.openpay.presentation.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.bumptech.glide.Glide
import com.danieer.galvez.openpay.R
import com.danieer.galvez.openpay.data.entities.Movie
import com.danieer.galvez.openpay.databinding.MovieDetailActivityBinding
import com.danieer.galvez.openpay.presentation.di.factory.ViewModelFactory
import dagger.android.AndroidInjection
import javax.inject.Inject

class MovieDetailActivity : ComponentActivity() {

    private lateinit var movieDetailsBinding: MovieDetailActivityBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    var movie: Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        movieDetailsBinding = MovieDetailActivityBinding.inflate(layoutInflater)
        setContentView(movieDetailsBinding.root)
        movie = intent.getSerializableExtra("MOVIE") as? Movie
        setupViews()
        setupMovieDetails()
    }

    private fun setupViews() {
        movieDetailsBinding.apply {
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        }
    }

    private fun setupMovieDetails() {
        movie?.let {
            movieDetailsBinding.apply {
                textViewMovieTitle.text = it.title
                textViewOverview.text = it.overview
                Glide.with(this@MovieDetailActivity)
                    .load("https://image.tmdb.org/t/p/w200" + it.posterPath)
                    .into(imgViewMoviePoster)
            }

        }

    }


}