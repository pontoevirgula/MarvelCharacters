package com.chsltutorials.marvelcharacters.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chsltutorials.marvelcharacters.R
import com.chsltutorials.marvelcharacters.model.entity.Character
import com.chsltutorials.marvelcharacters.model.util.load
import kotlinx.android.synthetic.main.adapter_item_character.view.*

class CharactersAdapter : RecyclerView.Adapter<CharactersAdapter.ViewHolder>(){

    private val items = mutableListOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_character, parent, false))

    override fun getItemCount() = items.size

    fun addAll(character: Character){
        items.add(character)
        notifyItemInserted(items.lastIndex)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = items[position]
        holder.bind(character)
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(character: Character) {
            itemView.tvName.text = character.name
            itemView.ivThumbnail.load("${character.thumbnail.path}/standard_medium.${character.thumbnail.extension}")
        }

    }

}
