
package com.example.paginationtask.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.e.vencenttask.data.model.SearchResults
import com.e.vencenttask.data.repository.PageKeyedMovieDataSource
import com.example.paginationtask.data.source.remote.UserService

import kotlinx.coroutines.CoroutineScope

class MovieDataSourceFactory(
  private val movieService: UserService,
  private val scope: CoroutineScope
) : DataSource.Factory<Int, SearchResults.IndustryDetail>() {

  val sourceLiveData = MutableLiveData<PageKeyedMovieDataSource>()

  override fun create(): DataSource<Int, SearchResults.IndustryDetail> {
    val source = PageKeyedMovieDataSource(movieService, scope)
    sourceLiveData.postValue(source)
    return source
  }
}