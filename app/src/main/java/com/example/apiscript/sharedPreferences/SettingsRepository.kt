package com.example.apiscript.sharedPreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.apiscript.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SettingsRepository(context: Context, nomFitxer: String) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(nomFitxer, Context.MODE_PRIVATE)
    private val gson = Gson()
    fun guardarUsuarios(lista: List<User>) {
        val json = gson.toJson(lista)
        sharedPreferences.edit().putString("usuarios", json).apply()
    }

    fun obtenerUsuarios(): List<User> {
        val json = sharedPreferences.getString("usuarios", null) ?: return emptyList()
        val type = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(json, type)
    }
}