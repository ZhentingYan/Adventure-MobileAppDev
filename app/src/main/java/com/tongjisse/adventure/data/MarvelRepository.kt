package com.tongjisse.adventure.data.network

import com.tongjisse.adventure.data.MarvelRepositoryImpl
import com.tongjisse.adventure.data.Provider
import com.tongjisse.adventure.model.MarvelCharacter
import io.reactivex.Single

interface MarvelRepository {
    fun getAllCharacters(searchQuery: String? = ""): Single<List<MarvelCharacter>>

    companion object : Provider<MarvelRepository>() {
        override fun creator() = MarvelRepositoryImpl()
    }
}