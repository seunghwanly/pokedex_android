package io.seunghwanly.network

import io.seunghwanly.model.Pokemon
import io.seunghwanly.model.PokemonName
import io.seunghwanly.network.model.ApiResponse

interface PokemonNetworkDataSource {
    suspend fun fetchPokemons(
        limit: Int? = null,
        offset: Int? = null,
    ): ApiResponse<List<PokemonName>>

    suspend fun fetchPokemonById(id: Int): ApiResponse<Pokemon>
}