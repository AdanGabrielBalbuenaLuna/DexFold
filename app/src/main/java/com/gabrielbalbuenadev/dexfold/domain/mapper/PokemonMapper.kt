package com.gabrielbalbuenadev.dexfold.domain.mapper

import com.gabrielbalbuenadev.dexfold.data.local.entity.PokemonEntity
import com.gabrielbalbuenadev.dexfold.domain.model.Pokemon
import com.gabrielbalbuenadev.dexfold.domain.model.PokemonStats

object PokemonMapper {

    // 👇 Convierte una Entity de Room a un modelo de dominio limpio
    fun fromEntity(entity: PokemonEntity): Pokemon {
        return Pokemon(
            id = entity.id,
            name = entity.name
                .replaceFirstChar { it.uppercase() }, // bulbasaur → Bulbasaur
            imageUrl = entity.imageUrl,

            // 👇 "grass,poison" → ["grass", "poison"]
            types = if (entity.types.isEmpty()) {
                emptyList()
            } else {
                entity.types.split(",")
            },

            // 👇 La API manda decímetros, convertimos a metros
            // 7 decímetros → 0.7 metros
            height = entity.height / 10f,

            // 👇 La API manda hectogramos, convertimos a kg
            // 69 hectogramos → 6.9 kg
            weight = entity.weight / 10f,

            stats = parseStats(entity.stats)
        )
    }

    // 👇 Convierte una lista de Entities de una sola vez
    fun fromEntityList(entities: List<PokemonEntity>): List<Pokemon> {
        return entities.map { fromEntity(it) }
    }

    // 👇 "hp:45,attack:49,defense:49,..." → PokemonStats
    private fun parseStats(stats: String): PokemonStats {

        // Si no hay stats aún (solo tenemos datos de la lista)
        if (stats.isEmpty()) return PokemonStats(0, 0, 0, 0, 0, 0)

        // Convierte "hp:45,attack:49" a un Map para acceso fácil
        // {"hp" → 45, "attack" → 49, ...}
        val statsMap = stats.split(",").associate { stat ->
            val (name, value) = stat.split(":")
            name to value.toInt()
        }

        return PokemonStats(
            hp             = statsMap["hp"]              ?: 0,
            attack         = statsMap["attack"]          ?: 0,
            defense        = statsMap["defense"]         ?: 0,
            specialAttack  = statsMap["special-attack"]  ?: 0,
            specialDefense = statsMap["special-defense"] ?: 0,
            speed          = statsMap["speed"]           ?: 0
        )
    }
}