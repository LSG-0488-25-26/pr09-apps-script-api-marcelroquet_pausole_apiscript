package com.example.apiscript.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.apiscript.model.Laptop
import com.example.apiscript.nav.Routes
import com.example.apiscript.viewmodel.LaptopViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    navController: NavController,
    viewModel: LaptopViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val company by viewModel.company.collectAsState()
    val product by viewModel.product.collectAsState()
    val typeName by viewModel.typeName.collectAsState()
    val inches by viewModel.inches.collectAsState()
    val screenResolution by viewModel.screenResolution.collectAsState()
    val cpu by viewModel.cpu.collectAsState()
    val ram by viewModel.ram.collectAsState()
    val memory by viewModel.memory.collectAsState()
    val gpu by viewModel.gpu.collectAsState()
    val price by viewModel.price.collectAsState()
    val loading by viewModel.loading.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Laptop") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Routes.LaptopScreen.route) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tornar")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (loading) {
                CircularProgressIndicator()
            } else {

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = inches,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { viewModel.onInchesChange(it) },
                        label = { Text("Inches") },
                        modifier = Modifier.weight(1f)

                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = company,
                        onValueChange = { viewModel.onCompanyChange(it) },
                        label = { Text("Company") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = typeName,
                        onValueChange = { viewModel.onTypeNameChange(it) },
                        label = { Text("Type") },
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = product,
                    onValueChange = { viewModel.onProductChange(it) },
                    label = { Text("Product") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = screenResolution,
                    onValueChange = { viewModel.onScreenResolutionChange(it) },
                    label = { Text("Resolution") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = cpu,
                    onValueChange = { viewModel.onCpuChange(it) },
                    label = { Text("CPU") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = ram,
                        onValueChange = { viewModel.onRamChange(it) },
                        label = { Text("RAM") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = memory,
                        onValueChange = { viewModel.onMemoryChange(it) },
                        label = { Text("Memory") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = gpu,
                        onValueChange = { viewModel.onGpuChange(it) },
                        label = { Text("GPU") },
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = price,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { viewModel.onPriceChange(it) },
                        label = { Text("Price") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = {
                        scope.launch {
                            val laptop = Laptop(
                                0,
                                Company = company,
                                Product = product,
                                TypeName = typeName,
                                Inches = inches.toFloatOrNull() ?: 0f,
                                ScreenResolution = screenResolution,
                                Cpu = cpu,
                                Ram = ram,
                                Memory = memory,
                                Gpu = gpu,
                                Price = price.toFloatOrNull() ?: 0f
                            )

                            val response = viewModel.addNewLaptop(laptop)

                            Toast.makeText(
                                context,
                                response.message ?: "Ordinador afegit",
                                Toast.LENGTH_SHORT
                            ).show()

                            viewModel.onIdChange("")
                            viewModel.onCompanyChange("")
                            viewModel.onProductChange("")
                            viewModel.onTypeNameChange("")
                            viewModel.onInchesChange("")
                            viewModel.onScreenResolutionChange("")
                            viewModel.onCpuChange("")
                            viewModel.onRamChange("")
                            viewModel.onMemoryChange("")
                            viewModel.onGpuChange("")
                            viewModel.onPriceChange("")
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}