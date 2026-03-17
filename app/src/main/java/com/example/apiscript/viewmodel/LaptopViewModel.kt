package com.example.apiscript.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiscript.BuildConfig
import com.example.apiscript.api.RetrofitInstance
import com.example.apiscript.model.Laptop
import com.example.apiscript.model.User
import com.example.apiscript.sharedPreferences.SettingsRepository
import com.example.googleappsscriptapidemo.model.PostRequest
import com.example.googleappsscriptapidemo.model.PostResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaptopViewModel(
    private val repository: SettingsRepository
): ViewModel() {
    // Variables per a peticions GET
    private val _laptops = MutableStateFlow<List<Laptop>>(emptyList())
    val laptops: StateFlow<List<Laptop>> = _laptops.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _priceFilter = MutableLiveData(0.0f)
    val priceFilter = _priceFilter

    private val _textPriceFilter = MutableLiveData(" ")
    val textPriceFilter = _textPriceFilter



    fun updatePrice(newPrice: Float) {
        priceFilter.value = newPrice
        textPriceFilter.value = newPrice.toString()
    }

    fun updateTextPrice(newPrice: String) {
        textPriceFilter.value = newPrice
        newPrice.toFloatOrNull()?.let { num ->
            if (num in 175f..6100f) {
                priceFilter.value = num
            }
        }
    }

    // Variables per a peticions POST
    private val _missatgeResposta = MutableStateFlow<PostResponse?>(null)
    val missatgeResposta: StateFlow<PostResponse?> = _missatgeResposta

    private val _loading = MutableStateFlow<Boolean>(false)
    val loading: StateFlow<Boolean> = _loading

    fun toggleLoading() {
        _loading.value = !_loading.value
    }

    fun setLoading(status: Boolean) {
        _loading.value = status
    }

    fun carregarDades(apiKey: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val resposta = RetrofitInstance.api.getDadesLaptop(apiKey)
                if (resposta.status == "ok" && resposta.data != null) {
                    _laptops.value = resposta.data
                    println("Dades rebudes: ${resposta.data}")
                } else {
                    _error.value = resposta.error ?: "Error desconegut"
                }
            } catch (e: Exception) {
                _error.value = e.message
                println("Error al executar carregarDades: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    fun carregarDadesMaxPrice(apiKey: String, maxPrice: Int) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val resposta = RetrofitInstance.api.getLaptopsPerPreu(apiKey, maxPrice = maxPrice)
                if (resposta.status == "ok" && resposta.data != null) {
                    val laptopList: List<Laptop> = resposta.data
                    _laptops.value = laptopList.sortedBy { it.Price }
                    println("Dades rebudes: ${resposta.data}")
                } else {
                    _error.value = resposta.error ?: "Error desconegut"
                }
            } catch (e: Exception) {
                _error.value = e.message
                println("Error al executar carregarDades: ${e.message}")
            } finally {
                _loading.value = false
            }
        }
    }

    suspend fun addNewLaptop(newLaptop: Laptop): PostResponse {
        _loading.value = true
        return try {
            val resposta = RetrofitInstance.api.afegirLaptop(BuildConfig.API_KEY, newLaptop)

            _missatgeResposta.value = resposta

            if (resposta.status != "ok") {
                resposta.copy(message = resposta.message)
            } else {
                resposta
            }

        } catch (e: Exception) {
            e.printStackTrace()
            println("❌ Error en registrar un portàtil: ${e.localizedMessage}")
            PostResponse(
                status = "error",
                message = e.localizedMessage ?: "Error de connexió amb el servidor",
            )
        } finally {
            _loading.value = false
        }
    }

    /*AUTH*/
    var nombre by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var isCorrect by mutableStateOf(true)
        private set

    var nombreLog by mutableStateOf("")
        private set
    var passwordLog by mutableStateOf("")
        private set
    var isCorrectLog by mutableStateOf(true)
        private set

    fun updateNombre(newNombre: String) {
        nombre = newNombre
    }

    fun updatePassword(newPassword: String) {
        password = newPassword
    }

    fun updateNombreLog(newNombre: String) {
        nombreLog = newNombre
    }

    fun updatePasswordLog(newPassword: String) {
        passwordLog = newPassword
    }

    fun registrarUsuario() {
        isCorrect = true

        if (nombre.isBlank() || password.isBlank()) {
            isCorrect = false
            return
        }
        val listaActual = repository.obtenerUsuarios().toMutableList()

        val existe = listaActual.any { it.name == nombre }
        if (existe) {
            isCorrect = false
            return
        }

        val nuevoUsuario = User(nombre, password)
        listaActual.add(nuevoUsuario)
        repository.guardarUsuarios(listaActual)
    }

    fun loginUsuer(): Boolean
    {
        if (nombreLog.isBlank() || passwordLog.isBlank()) {
            return false
        }
        val listaActual = repository.obtenerUsuarios().toMutableList()

        val exist = listaActual.any { it.name == nombreLog && it.password == passwordLog }

        return exist

    }
}