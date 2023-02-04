package com.study.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.study.common.State
import com.study.domain.model.Movie
import com.study.domain.usecase.MovieUsecases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PopularMoviesViewModel(
    private val movieUsecases: MovieUsecases // todo change to getMoviesUseCase
) : ViewModel() {

    private var _movies: MutableStateFlow<State<List<Movie>>> =
        MutableStateFlow(State.Loading())
    val movies = _movies.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() = viewModelScope.launch {
        movieUsecases.getTopMovies().collectLatest { moviesResource ->
            _movies.value = moviesResource
        }
    }

}
