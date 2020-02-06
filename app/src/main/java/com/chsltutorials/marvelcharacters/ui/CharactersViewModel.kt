package com.chsltutorials.marvelcharacters.ui

import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.chsltutorials.marvelcharacters.model.api.MarvelAPI
import com.chsltutorials.marvelcharacters.model.entity.Character
import com.chsltutorials.marvelcharacters.model.paging.CharactersDataSourceFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CharactersViewModel : ViewModel(){

    var characterList: Observable<PagedList<Character>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 20

    private val sourceFactory: CharactersDataSourceFactory

    init {
        sourceFactory = CharactersDataSourceFactory(compositeDisposable, MarvelAPI.getService())

        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPrefetchDistance(10)
            .setEnablePlaceholders(false)
            .build()

        characterList = RxPagedListBuilder(sourceFactory, config)
            .setFetchScheduler(Schedulers.io())
            .buildObservable()
            //.cache()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}
