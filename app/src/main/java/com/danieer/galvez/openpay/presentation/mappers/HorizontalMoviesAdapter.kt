package com.danieer.galvez.openpay.presentation.mappers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.danieer.galvez.openpay.data.entities.Movie
import com.danieer.galvez.openpay.databinding.HorizontalMovieItemViewBinding


class HorizontalMoviesAdapter(
    private val clickListener: MoviesAdapter.ClickListener
) : RecyclerView.Adapter<HorizontalMoviesAdapter.HorizontalMovieViewHolder>() {

    private var moviesList: List<Movie> = emptyList()

    inner class HorizontalMovieViewHolder(val binding: HorizontalMovieItemViewBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface ClickListener {
        fun onElementClicked(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalMovieViewHolder {
        val binding =
            HorizontalMovieItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return HorizontalMovieViewHolder(binding)
    }

    override fun getItemCount(): Int = moviesList.size

    override fun onBindViewHolder(holder: HorizontalMovieViewHolder, position: Int) {
        with(holder) {
            val movie = moviesList[position]
            binding.apply {
                textViewMovieName.text = movie.title
                Glide.with(imgViewMovie.context)
                    .load("https://image.tmdb.org/t/p/w200" + movie.posterPath).into(imgViewMovie)
            }
            holder.itemView.setOnClickListener { clickListener.onElementClicked(movie) }


        }
    }

    fun setMovieList(movieList: List<Movie>) {
        this.moviesList = movieList
        notifyDataSetChanged()
    }
}