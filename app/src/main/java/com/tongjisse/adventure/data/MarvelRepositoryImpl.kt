package com.tongjisse.adventure.data

import com.tongjisse.adventure.data.network.MarvelApi
import com.tongjisse.adventure.data.network.MarvelRepository
import com.tongjisse.adventure.data.network.provider.retrofit
import com.tongjisse.adventure.model.MarvelCharacter
import io.reactivex.Single

class MarvelRepositoryImpl : MarvelRepository {
    val api = retrofit.create(MarvelApi::class.java)

    override fun getAllCharacters(searchQuery: String?): Single<List<MarvelCharacter>> = api.getCharacters(
            offset = 0,
            searchQuery = searchQuery,
            limit = elementsOnListLimit
    ).map {
        it.data?.results.orEmpty().map(::MarvelCharacter)
    }

    companion object {
        const val elementsOnListLimit = 50
    }
}