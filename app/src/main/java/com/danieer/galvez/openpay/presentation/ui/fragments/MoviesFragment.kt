package com.danieer.galvez.openpay.presentation.ui.fragments

import android.content.Context
import android.content.Intent
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
        viewModel.getPopularMovies()
    }

    private fun setupViews() {
        binding.recyclerViewPopularMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = moviesAdapter
        }
    }

    private fun setupObservers() {
        viewModel.moviesData.observe(viewLifecycleOwner) {
            moviesAdapter.setMovieList(it.movies.take(5))
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
}