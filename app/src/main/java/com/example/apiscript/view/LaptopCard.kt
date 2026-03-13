package com.example.apiscript.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apiscript.model.Laptop

/**
 * Funció composable que crea una composable Card per a cada objecte Arc rebut per paràmetre.
 * @author RIS
 * @param arc de tipus Arc
 */
@Composable
fun LaptopCard(laptop: Laptop) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = laptop.Product,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Companyia: ${laptop.Company}")
            Text("Cpu: ${laptop.Cpu}")
            Text("GPU: ${laptop.Gpu}")
            Text("RAM: ${laptop.Ram}")
            Text("Preu: ${laptop.Price} €")
        }
    }
}