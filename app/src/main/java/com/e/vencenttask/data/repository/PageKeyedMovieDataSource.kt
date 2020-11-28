
package com.e.vencenttask.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.e.vencenttask.data.model.SearchResults.IndustryDetail
import com.e.vencenttask.network.NetworkState
import com.example.paginationtask.data.source.remote.UserService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Collections.emptyList

class PageKeyedMovieDataSource(
  private val movieService: UserService,
  private val scope: CoroutineScope
) : PageKeyedDataSource<Int, IndustryDetail>() {

  private var retry: (() -> Any)? = null

  /**
   * There is no sync on the state because paging will always call loadInitial first then wait
   * for it to return some success value before calling loadAfter.
   */
  val networkState = MutableLiveData<NetworkState>()
  val initialLoad = MutableLiveData<NetworkState>()

  fun retryAllFailed() {
    val prevRetry = retry
    retry = null
    prevRetry?.let {
      scope.launch { it.invoke() }
    }
  }

  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, IndustryDetail>
  ) {
    scope.launch {
      networkState.postValue(NetworkState.LOADING)
      initialLoad.postValue(NetworkState.LOADING)

      try {
        val prev = 1
        val next = prev
        val response = movieService.discover()
        val data = response.body()
        val results = data?.dataList ?: emptyList()
        retry = null
        networkState.postValue(NetworkState.LOADED)
        initialLoad.postValue(NetworkState.LOADED)
        callback.onResult(results, prev, next)
      } catch (e: Exception) {
        retry = { loadInitial(params, callback) }
        val error = NetworkState.error(e.message ?: "Unknown error")
        networkState.postValue(error)
        initialLoad.postValue(error)
        Log.e(javaClass.name, "loadInitial: ${e.message}")
      }
    }
  }

  override fun loadBefore(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, IndustryDetail>
  ) {
    // ignore
  }

  override fun loadAfter(
    params: LoadParams<Int>,
    callback: LoadCallback<Int, IndustryDetail>
  ) {
    /*scope.launch {
      networkState.postValue(NetworkState.LOADING)
      try {
        val response = movieService.discover()
        if (response.isSuccessful) {
          val items = response.body()?.dataList ?: emptyList()
          val adjacent = params.key
          retry = null
          callback.onResult(items, adjacent)
          networkState.postValue(NetworkState.LOADED)
        }
      } catch (e: Exception) {
        retry = { loadAfter(params, callback) }
        networkState.postValue(NetworkState.error(e.message ?: "Unknown error"))
        Log.e(javaClass.name, "loadAfter: ${e.message}")
      }
    }*/
  }


}