package com.example.predictheartdisease.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.predictheartdisease.HeartDiseaseModel
import com.example.predictheartdisease.screens.CreateAccount
import com.example.predictheartdisease.screens.PredictionScreen

@Composable
fun AppNav() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Register.route
    ) {
        composable(Screens.Register.route) {
            CreateAccount(
                onCreated = {
                    navController.navigate(Screens.Prediction.route) {
                        popUpTo(Screens.Register.route) { inclusive = true }
                    }
                },
                onSignInClick = {
                    navController.navigate(Screens.Prediction.route)
                }
            )
        }

        composable(Screens.Prediction.route) {
            val context = LocalContext.current
            val model = remember { HeartDiseaseModel(context, "logistic_model.onnx") }
            PredictionScreen(model = model)
        }
    }
}
