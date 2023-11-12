package com.danieer.galvez.openpay.presentation.mappers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danieer.galvez.openpay.data.entities.Movie
import com.danieer.galvez.openpay.databinding.MovieItemViewBinding

class MoviesAdapter(
    private val clickListener: MoviesAdapter.ClickListener
) : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var moviesList: List<Movie> = emptyList()

    inner class MovieViewHolder(val binding: MovieItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ClickListener {
        fun onElementClicked(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding =
            MovieItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        with(holder) {
            val movie = moviesList[position]
            binding.apply {
                textViewMovieName.text = movie.title
                Glide.with(imgViewMovie.context)
                    .load("https://image.tmdb.org/t/p/w200" + movie.posterPath)
                    .into(imgViewMovie)
            }
            holder.itemView.setOnClickListener { clickListener.onElementClicked(movie) }


        }
    }

    fun setMovieList(movieList: List<Movie>) {
        this.moviesList = movieList
        notifyDataSetChanged()
    }
}