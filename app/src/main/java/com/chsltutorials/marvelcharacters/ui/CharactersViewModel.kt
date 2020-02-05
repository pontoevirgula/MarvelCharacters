package com.chsltutorials.marvelcharacters.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.chsltutorials.marvelcharacters.model.api.MarvelAPI
import com.chsltutorials.marvelcharacters.model.entity.Character
import io.reactivex.Observable

class CharactersViewModel : ViewModel(){

    var isLoading : Boolean = false
        private set

    var currentPage = -1
        private set

    private val characters = mutableListOf<Character>()

    fun load(page : Int) : Observable<Character> {
        isLoading = true
        Log.d("CHSL","página: $page | pagina atual: $currentPage")

        return if(page <= currentPage) {
            Observable.fromIterable(characters)
        }else {
            currentPage = page
            MarvelAPI.getService().allCharacters(page * 20)
                .flatMapIterable {response ->
                    response.data.results
                }
                .doOnNext { c->
                    characters.add(c)
                    Observable.just(c)
                }
        }.doOnComplete { isLoading = false }
    }

    fun reset(){
        isLoading = false
        currentPage = -1
        characters.clear()
    }
}
