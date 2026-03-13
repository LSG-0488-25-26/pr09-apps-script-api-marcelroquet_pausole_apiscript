package com.example.apiscript.model

/**
 * Data class amb el mateix número de columnes,
 * mateix nom de columnes i mateix tipus de dades
 * que les columnes originals que hi ha al document de Google Sheets
 */
data class Laptop(
    val Id : Int,
    val Company : String,
    val Product : String,
    val TypeName : String,
    val Inches : Float,
    val ScreenResolution : String,
    val Cpu : String,
    val Ram : String,
    val Memory : String,
    val Gpu : String,
    val Price : Float,
    )