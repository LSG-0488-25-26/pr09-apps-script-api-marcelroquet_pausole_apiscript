package com.example.apiscript.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.apiscript.view.FormScreen
import com.example.apiscript.view.LaptopScreen
import com.example.apiscript.view.LogInScreen
import com.example.apiscript.view.RegisterScreen
import com.example.apiscript.viewmodel.LaptopViewModel

@Composable
fun EntryPoint(
    viewModel: LaptopViewModel,
    apiKey: String,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Routes.RegisterScreen.route
    ){
        composable(Routes.LaptopScreen.route) {
            LaptopScreen(viewModel = viewModel, apiKey = apiKey, navController = navController)
        }
        composable(Routes.LogInScreen.route) {
            LogInScreen(viewModel,navController)
        }
        composable(Routes.RegisterScreen.route) {
            RegisterScreen(viewModel = viewModel, navController = navController)
        }
        composable(Routes.FormScreen.route) {
            FormScreen(viewModel = viewModel, navController = navController)
        }
    }
}