import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.apiscript.BuildConfig
import com.example.apiscript.viewmodel.LaptopViewModel
import kotlin.math.absoluteValue

@Composable
fun SearchForPrice(viewModel: LaptopViewModel ) {

    val price by viewModel.priceFilter.observeAsState(0.0f)
    val textPrice by viewModel.textPriceFilter.observeAsState("")
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Slider(
            value = price.absoluteValue,
            onValueChange = { viewModel.updatePrice(it) },
            valueRange = 175f..6100f,
            modifier = Modifier.weight(1f)
        )

        OutlinedTextField(
            value = textPrice.toString(),
            onValueChange = { viewModel.updateTextPrice(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.width(80.dp),
            label = { Text("€") }
        )

        Button(
            onClick = {
                val finalPrice = price.toInt()
                viewModel.carregarDadesMaxPrice(BuildConfig.API_KEY, finalPrice)
            }
        ) {
            Text("Filtrar")
        }
    }
}