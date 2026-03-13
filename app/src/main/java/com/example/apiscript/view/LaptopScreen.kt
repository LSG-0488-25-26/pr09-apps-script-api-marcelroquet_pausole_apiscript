package com.example.apiscript.view

import SearchForPrice
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apiscript.model.Laptop
import com.example.apiscript.viewmodel.LaptopViewModel

/**
 * View que es carrega en primera instància des del MainActivity.kt
 * Se subscriu a les variables públiques del VM
 * Executa el mètode del VM que farà una petició de GET a la API usant la API_KEY
 * @author RIS
 * @param viewModel VM
 * @param apiKey API_KEY provinent de secrets.properties
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LaptopScreen(
    viewModel: LaptopViewModel = viewModel(),
    apiKey: String // Rep la API_KEY des de MainActivity.kt
) {
    // Subscripció a les variables públiques del VM
    val laptops by viewModel.laptops.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Crida automàtica quan s'obre la pantalla
    LaunchedEffect(Unit) {
        viewModel.carregarDades(apiKey)
//        viewModel.addNewLaptop(Laptop(
//            1,
//            "companyia",
//            "producte",
//            "tipus",
//            10.0f,
//            "16x9",
//            "CPu",
//            "8GB",
//            "512GB",
//            "GPU",
//            1000.0f
//
//
//        ))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Laptops") }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            // contentAlignment = Layout.Alignment.Center
        ) {

            when {
                // Mentre loading sigui 'true' mostrarem un spinner de càrrega
                loading -> {
                    CircularProgressIndicator()
                }

                // Quan hi hagi algun error a la resposta de la API, mostrarem un error.
                error != null -> {
                    Text(
                        text = "Error: $error",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Quan no hi hagi dades a la pestanya "Full 1" del Google Sheets, mostrarem això:
                laptops.isEmpty() -> {
                    Text("No hi ha dades disponibles")
                }

                // En qualsevol altre cas, és a dir: tot ha funcionat bé, crearem una LazyColumn amb tantes Cards com Arcs hi hagi a dins de la resposta GET.
                else -> {
                    Column {
                        SearchForPrice(viewModel)
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(laptops) { laptop ->
                                LaptopCard(laptop)
                            }
                        }
                    }
                }
            }
        }
    }
}