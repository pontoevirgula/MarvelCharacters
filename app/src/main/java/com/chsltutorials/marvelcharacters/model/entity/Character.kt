package com.chsltutorials.marvelcharacters.model.entity

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)