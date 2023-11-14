package com.danieer.galvez.openpay.presentation.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.danieer.galvez.openpay.data.entities.Movie
import com.danieer.galvez.openpay.databinding.FragmentMoviesBinding
import com.danieer.galvez.openpay.presentation.di.factory.ViewModelFactory
import com.danieer.galvez.openpay.presentation.mappers.DataState
import com.danieer.galvez.openpay.presentation.mappers.HorizontalMoviesAdapter
import com.danieer.galvez.openpay.presentation.mappers.MoviesAdapter
import com.danieer.galvez.openpay.presentation.ui.activity.MovieDetailActivity
import com.danieer.galvez.openpay.presentation.ui.viewmodel.MoviesFragmentViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class MoviesFragment : Fragment(), MoviesAdapter.ClickListener {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: MoviesFragmentViewModel

    private var moviesAdapter = HorizontalMoviesAdapter(this)
    private var ratedMoviesAdapter = HorizontalMoviesAdapter(this)
    private var upcomingMoviesAdapter = HorizontalMoviesAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[MoviesFragmentViewModel::class.java]
        setupViews()
        setupObservers()
        viewModel.getPopularMovies(isNetworkAvailable())
        viewModel.getRatedMovies(isNetworkAvailable())
        viewModel.getUpcomingMovies(isNetworkAvailable())
    }

    private fun setupViews() {
        binding.run {
            recyclerViewPopularMovies.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = moviesAdapter
            }
            recyclerViewRatedMovies.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = ratedMoviesAdapter
            }
            recyclerViewUpcomingMovies.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                adapter = upcomingMoviesAdapter
            }
        }
    }

    private fun setupObservers() {
        viewModel.moviesData.observe(viewLifecycleOwner) {
            moviesAdapter.setMovieList(it.movies.take(5))
        }
        viewModel.ratedMoviesData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> Unit
                is DataState.Success -> ratedMoviesAdapter.setMovieList(it.data.movies.take(5))
                is DataState.Error -> {
                    hideSection()
                }

                else -> Unit
            }
        }
        viewModel.upcomingMoviesData.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> Unit
                is DataState.Success -> upcomingMoviesAdapter.setMovieList(it.data.movies.take(5))
                is DataState.Error -> {
                    hideUpcomingSection()
                }

                else -> Unit
            }
        }
    }

    private fun hideSection() {
        binding.apply {
            textViewRated.visibility = View.INVISIBLE
            recyclerViewRatedMovies.visibility = View.INVISIBLE
        }
    }

    private fun hideUpcomingSection() {
        binding.apply {
            textViewUpcoming.visibility = View.INVISIBLE
            recyclerViewUpcomingMovies.visibility = View.INVISIBLE
        }
    }

    override fun onElementClicked(movie: Movie) {
        goToDetails(movie)
    }

    private fun goToDetails(movie: Movie) {
        val intent = Intent(requireContext(), MovieDetailActivity::class.java)
        intent.putExtra("MOVIE", movie)
        startActivity(intent)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }
    }
}