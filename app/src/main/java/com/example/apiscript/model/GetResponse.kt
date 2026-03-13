package com.example.apiscript.model

/**
 * Data class creat per a recollir la resposta de la petició GET
 * feta des de la app d'Android cap a la API de JS d'Apps Script
 * És un data class parametritzat amb el tipus genèric <T>
 *     cosa que permetria capturar respostes de GET per a
 *     diferents conjunts de dades; ja que a on hi ha les dades
 *     a tractar és a l'atribut GetResponse.data que s'adapta al
 *     tipus de dades que li passem a GetResponse al invocar-lo.
 */
data class GetResponse<T>(
    val status: String,
    val type: String? = null,
    val data: T? = null,
    val error: String? = null
)