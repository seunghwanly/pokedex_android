package io.seunghwanly.model

data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val order: Int,
    val weight: Int,
    val abilities: List<PokemonAbility>,
    val sprites: List<PokemonSprite>,
    val types: List<PokemonType>
)
