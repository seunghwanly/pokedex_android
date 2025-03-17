package io.seunghwanly.model

// {
//      "ability": {
//          "name": "inner-focus",
//          "url": "https://pokeapi.co/api/v2/ability/39/"
//      },
//      "is_hidden": false,
//      "slot": 1
// }
data class PokemonAbility(
    val isHidden: Boolean,
    val slot: Int,
    val ability: String
)
