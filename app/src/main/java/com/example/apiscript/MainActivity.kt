package com.example.apiscript

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.apiscript.nav.EntryPoint
import com.example.apiscript.sharedPreferences.SettingsRepository
import com.example.apiscript.ui.theme.ApiScriptTheme
import com.example.apiscript.view.LaptopScreen
import com.example.apiscript.viewmodel.LaptopViewModel
import com.example.apiscript.viewmodel.LaptopViewModelFactory
import kotlin.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = SettingsRepository(this, "user_prefs")
        val viewModel: LaptopViewModel by viewModels {
            LaptopViewModelFactory(repository)
        }
        setContent {
            ApiScriptTheme {
                val navController = rememberNavController()
                EntryPoint(viewModel = viewModel, apiKey = BuildConfig.API_KEY, navController)
            }
        }
    }
}



