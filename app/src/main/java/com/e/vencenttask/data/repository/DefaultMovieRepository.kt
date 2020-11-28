package com.example.paginationtask.data.repository

import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.e.vencenttask.data.model.SearchResults.IndustryDetail
import com.e.vencenttask.data.source.IndustryRepository
import com.e.vencenttask.data.Result
import com.example.paginationtask.data.source.remote.UserService
import kotlinx.coroutines.CoroutineScope


class DefaultMovieRepository(
    private val service: UserService,
    private val scope: CoroutineScope
) : IndustryRepository {

    override fun getindustryData(): Result<IndustryDetail> {
        val dataSourceFactory = MovieDataSourceFactory(service, scope)

        val config = PagedList.Config.Builder()
            .setPageSize(1)
            .setEnablePlaceholders(true)
            .build()

        return Result(
            pagedList = LivePagedListBuilder<Int, IndustryDetail>(dataSourceFactory, config).build(),
            networkState = Transformations.switchMap(dataSourceFactory.sourceLiveData) { it.networkState },
            refreshState = Transformations.switchMap(dataSourceFactory.sourceLiveData) { it.initialLoad },
            retry = { dataSourceFactory.sourceLiveData.value?.retryAllFailed() },
            refresh = { dataSourceFactory.sourceLiveData.value?.invalidate() }
        )
    }
}