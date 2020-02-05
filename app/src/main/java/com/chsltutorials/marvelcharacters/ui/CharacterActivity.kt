package com.chsltutorials.marvelcharacters.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chsltutorials.marvelcharacters.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_character.*

class CharacterActivity : AppCompatActivity() {

    private val viewModel: CharactersViewModel by lazy {
        ViewModelProviders.of(this).get(CharactersViewModel::class.java)
    }

    private val adapter: CharactersAdapter by lazy { CharactersAdapter() }

    //guarda posição do scroll
    private var recyclerState : Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        val linearLayoutManager = LinearLayoutManager(this)
        rvCharacters.layoutManager = linearLayoutManager
        rvCharacters.adapter = adapter
        rvCharacters.addOnScrollListener( object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition == adapter.itemCount - 1 && !viewModel.isLoading){
                    Log.d("CHSL","Carregando mais...")
                    loadCharacters(viewModel.currentPage + 1)
                }
            }
        })

        loadCharacters(0)
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState",rvCharacters.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState.getParcelable("lmState")
    }

    private fun loadCharacters(page: Int) {
        val disposable = viewModel.load(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { character->
                    adapter.addAll(character)
                    Log.d("CHSL","${character.id} - ${character.name}")
                },
                { e->
                    Log.e("CHSL", e.message!!)
                },
                {
                    recyclerState.let {
                        rvCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                    }
                }
            )
    }

}
