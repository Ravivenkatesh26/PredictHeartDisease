package com.example.predictheartdisease.navigation

sealed class Screens(val route: String) {
    data object Register : Screens("register")
    data object Prediction : Screens("prediction")
}
