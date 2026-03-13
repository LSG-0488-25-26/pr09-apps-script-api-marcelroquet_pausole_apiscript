package com.example.googleappsscriptapidemo.model

/**
 * Data class incomplet que servirà per a recollir la resposta
 * de la petició POST d'Apps Script.
 */
data class PostResponse(
    val status: String,
    val message: String
)
