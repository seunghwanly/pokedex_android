package io.seunghwanly.model

import io.seunghwanly.model.sprite.OtherSprite

data class PokemonSprite(
    val backDefault: String,
    val backFemale: String,
    val backShiny: String,
    val backShinyFemale: String,
    val frontDefault: String,
    val frontFemale: String,
    val frontShiny: String,
    val frontShinyFemale: String,
    val other: OtherSprite
)



