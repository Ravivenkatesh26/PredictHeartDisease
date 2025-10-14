package com.example.predictheartdisease

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun PredictionScreen(model: HeartDiseaseModel) {
    val features = remember { mutableStateListOf("","", "", "", "", "", "", "", "") }
    val labels = listOf(
        "Name","Sex", "Chest Pain Type", "Max Heart Rate", "Exercise Induced Angina",
        "ST Depression", "Slope", "CA", "Thal"
    )

    var predictionResult by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        labels.forEachIndexed { index, label ->
            FeatureTextField(features[index], label) { features[index] = it }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                val featureFloats = features.drop(1).map { it.toFloatOrNull() ?: 0f }.toFloatArray()
                coroutineScope.launch(Dispatchers.Default) {
                    val prediction = model.predict(featureFloats)
                    predictionResult = if (prediction >= 0.5f) "Yes" else "No"
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Predict")
        }

        Spacer(modifier = Modifier.height(16.dp))

        predictionResult?.let {
            Text(
                text = "Heart Disease: $it",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}