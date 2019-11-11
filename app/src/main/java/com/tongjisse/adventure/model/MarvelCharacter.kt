package com.tongjisse.adventure.model

import com.tongjisse.adventure.data.network.dto.CharacterMarvelDto

data class MarvelCharacter(
        val name: String,
        val imageUrl: String
) {
    constructor(dto: CharacterMarvelDto) : this(
            name = dto.name,
            imageUrl = dto.imageUrl
    )
}