package io.seunghwanly.model

// {
//  "base_stat": 40,
//  "effort": 0,
//  "stat": {
//      "name": "hp",
//      "url": "https://pokeapi.co/api/v2/stat/1/"
//  }
// }
data class PokemonStat(
    val baseStat: Int,
    val effort: Int,
    val statName: String
)
