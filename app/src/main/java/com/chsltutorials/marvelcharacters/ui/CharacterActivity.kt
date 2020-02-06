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

    private val adapter: CharacterAdapter by lazy { CharacterAdapter() }

    //guarda posição do scroll
    private var recyclerState : Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        val linearLayoutManager = LinearLayoutManager(this)
        with(rvCharacters) {
            this.layoutManager = linearLayoutManager
            this.adapter = adapter
        }

        subscribeToList()
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable("lmState",rvCharacters.layoutManager?.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        recyclerState = savedInstanceState.getParcelable("lmState")
    }

    private fun subscribeToList() {
        val disposable = viewModel.characterList
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { pagedList->
                    adapter.submitList(pagedList)
                    if (recyclerState != null) {
                        rvCharacters.layoutManager?.onRestoreInstanceState(recyclerState)
                        recyclerState = null
                    }
                },
                { e->
                    Log.e("CHSL", e.message!!)
                }
            )
    }

}
