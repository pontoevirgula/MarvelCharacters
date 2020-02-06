package com.chsltutorials.marvelcharacters.model.paging

import androidx.paging.DataSource
import com.chsltutorials.marvelcharacters.model.api.MarvelAPI
import com.chsltutorials.marvelcharacters.model.entity.Character
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val marvelApi: MarvelAPI
) : DataSource.Factory<Int, Character>() {

    override fun create(): DataSource<Int, Character> {
        return CharactersDataSource(marvelApi, compositeDisposable)
    }
}