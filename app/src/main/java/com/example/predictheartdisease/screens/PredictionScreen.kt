package com.example.predictheartdisease.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.predictheartdisease.Database.DatabaseProvider
import com.example.predictheartdisease.Data.HeartData
import com.example.predictheartdisease.FeatureTextField
import com.example.predictheartdisease.HeartDiseaseModel
import com.example.predictheartdisease.RecordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PredictionScreen(model: HeartDiseaseModel) {
    val features = remember { mutableStateListOf("","", "", "", "", "", "", "", "") }
    val labels = listOf(
        "Name","Sex", "Chest Pain Type", "Max Heart Rate", "Exercise Induced Angina",
        "ST Depression", "Slope", "CA", "Thal"
    )

    var predictionResult by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current
    val repo = remember {
        val dao = DatabaseProvider.getDatabase(context).dao()
        RecordsRepository(dao)
    }

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
                coroutineScope.launch(Dispatchers.Default) {
                    val featureFloats = features.drop(1).map { it.toFloatOrNull() ?: 0f }.toFloatArray()
                    val prediction = model.predict(featureFloats)
                    predictionResult = if (prediction >= 0.5f) "Yes" else "No"
                    val entity = HeartData(
                        name = features[0].ifBlank { "Unknown" },
                        sex = features[1].toFloatOrNull() ?: 0f,
                        chestPainType = features[2].toFloatOrNull() ?: 0f,
                        maxHeartRate = features[3].toFloatOrNull() ?: 0f,
                        exerciseInducedAngina = features[4].toFloatOrNull() ?: 0f,
                        sTDepression = features[5].toFloatOrNull() ?: 0f,
                        slope = features[6].toFloatOrNull() ?: 0f,
                        ca = features[7].toFloatOrNull() ?: 0f,
                        thal = features[8].toFloatOrNull() ?: 0f
                    )

                    withContext(Dispatchers.IO){
                        repo.insert(entity)
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Record saved", Toast.LENGTH_SHORT).show()
                    }
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