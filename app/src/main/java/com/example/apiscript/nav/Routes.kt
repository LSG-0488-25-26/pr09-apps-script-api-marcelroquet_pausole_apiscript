package com.example.apiscript.nav

sealed class Routes(val route: String) {
    object LaptopScreen : Routes("LaptopScreen")
    object LogInScreen : Routes("LogInScreen")
    object RegisterScreen : Routes("RegisterScreen")

}