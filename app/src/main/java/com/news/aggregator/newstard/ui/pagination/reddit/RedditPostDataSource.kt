package com.news.aggregator.newstard.ui.pagination.reddit

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.news.aggregator.newstard.network.NetworkState
import com.news.aggregator.newstard.repositories.reddit.RedditPost
import com.news.aggregator.newstard.repositories.reddit.RedditRepository
import com.news.aggregator.newstard.ui.pagination.base.ServiceDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RedditPostDataSource
        @Inject constructor(private val _redditRepository: RedditRepository):
        ServiceDataSource, ItemKeyedDataSource<String, RedditPost>() {

    companion object{
        const val PAGE_SIZE = 25
    }

    private val initialLoadStateLiveData = MutableLiveData<NetworkState>()
    private val paginationLoadStateLiveData = MutableLiveData<NetworkState>()

    private val compositeDisposable = CompositeDisposable()

    override fun getInitialLoadStateLiveData() = initialLoadStateLiveData

    override fun getPaginationLoadStateLiveData() = paginationLoadStateLiveData

    override fun clear() {
        compositeDisposable.clear()
    }

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<RedditPost>) {

        initialLoadStateLiveData.postValue(NetworkState.LOADING)

        val postListDisposable = _redditRepository.getPosts(PAGE_SIZE, null)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                initialLoadStateLiveData.postValue(NetworkState.SUCCESS)

                callback.onResult(it)
            }, {
                initialLoadStateLiveData.postValue(NetworkState.ERROR)
            })

        compositeDisposable.add(postListDisposable)
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {

        paginationLoadStateLiveData.postValue(NetworkState.LOADING)

        val postListDisposable = _redditRepository.getPosts(PAGE_SIZE, params.key)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                paginationLoadStateLiveData.postValue(NetworkState.SUCCESS)

                callback.onResult(it)
            }, {
                paginationLoadStateLiveData.postValue(NetworkState.ERROR)
            })

        compositeDisposable.add(postListDisposable)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<RedditPost>) {
        // Previous data will be already present.
    }

    override fun getKey(item: RedditPost) = item.id



}