package com.example.apiscript.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apiscript.api.RetrofitInstance
import com.example.apiscript.model.Laptop
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LaptopViewModel: ViewModel() {
    // Variables per a peticions GET
    private val _laptops = MutableStateFlow<List<Laptop>>(emptyList())
    val laptops: StateFlow<List<Laptop>> = _laptops.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    // Variables per a peticions POST
//    private val _missatgeResposta = MutableStateFlow<PostResponse?>(null)
//    val missatgeResposta: StateFlow<PostResponse?> = _missatgeResposta

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
}
