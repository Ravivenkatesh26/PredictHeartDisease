package com.example.predictheartdisease

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.predictheartdisease.navigation.AppNav
import com.example.predictheartdisease.screens.CreateAccount

class MainActivity : ComponentActivity() {

    private lateinit var model: HeartDiseaseModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize model
        model = HeartDiseaseModel(this, "logistic_model.onnx")

        setContent {
            //PredictionScreen(model)
            //CreateAccount()
            AppNav()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        model.close()
    }
}
