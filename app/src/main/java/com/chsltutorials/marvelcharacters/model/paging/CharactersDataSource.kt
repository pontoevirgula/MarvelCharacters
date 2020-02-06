package com.chsltutorials.marvelcharacters.model.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.chsltutorials.marvelcharacters.model.api.MarvelAPI
import com.chsltutorials.marvelcharacters.model.entity.Character
import io.reactivex.disposables.CompositeDisposable

class CharactersDataSource(
    private val marvelAPI: MarvelAPI,
    private val compositeDisposable: CompositeDisposable)
: PageKeyedDataSource<Int,Character>(){

        override fun loadInitial(params: LoadInitialParams<Int>,callback: LoadInitialCallback<Int, Character>) {
            val numberOfItems = params.requestedLoadSize
            createObservable(0,1,numberOfItems,callback,null)
        }

        override fun loadAfter(params: LoadParams<Int>,callback: LoadCallback<Int, Character>) {
            val numberOfItems = params.requestedLoadSize
            val page = params.key
            createObservable(page,page + 1,numberOfItems,null,callback)
        }

        override fun loadBefore(params: LoadParams<Int>,callback: LoadCallback<Int, Character>) {
            val numberOfItems = params.requestedLoadSize
            val page = params.key
            createObservable(page,page - 1,numberOfItems,null,callback)
        }

        private fun createObservable(
            requestedPage: Int,
            adjacentPage: Int,
            requestedLoadSize: Int,
            initialCallback: LoadInitialCallback<Int, Character>?,
            callback: LoadCallback<Int, Character>?
        ) {

            compositeDisposable.add(
                 marvelAPI.allCharacters(requestedPage * requestedLoadSize)
                 .subscribe(
                    { response ->
                        Log.d("CHSL", "Pagina carregada: $requestedPage")
                        initialCallback?.onResult(response.data.results,null,adjacentPage)
                        callback?.onResult(response.data.results,adjacentPage)
                    },
                    { t->
                        Log.d("CHSL","Erro ao carregar pagina: $requestedPage", t)
                    }
                )
            )
        }

}
