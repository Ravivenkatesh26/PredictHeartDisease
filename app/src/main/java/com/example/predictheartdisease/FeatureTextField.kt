package com.example.predictheartdisease

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeatureTextField(value: String, label: String, onValueChange: (String) -> Unit) {
    when(label){
        "Chest Pain Type"->{
            val options = listOf(
                "Typical Angina",
                "Atypical Angina",
                "Non-anginal Pain",
                "Asymptomatic"
            )
            val selectedText = when(value){
                "0"->"Typical Angina"
                "1"->"Atypical Angina"
                "2"->"Non-anginal Pain"
                "3"->"Asymptomatic"
                else -> label
            }
            ExposedDropDownMenu(options,selectedText,label,onValueChange)
        }
        "Sex"->{
            val options = listOf("Female","Male")
            val selectedText = when(value){
                "0"->"Female"
                "1"->"Male"
                else -> label
            }
            ExposedDropDownMenu(options,selectedText,label,onValueChange)
        }
        "Exercise Induced Angina"->{
            val options = listOf("No","Yes")
            val selectedText = when(value){
                "0"->"No"
                "1"->"Yes"
                else -> label
            }
            ExposedDropDownMenu(options,selectedText,label,onValueChange)
        }
        "Slope"->{
            val options = listOf("Upsloping","Flat","Downsloping")
            val selectedText = when(value){
                "0"->"Upsloping"
                "1"->"Flat"
                "2" -> "Downsloping"
                else -> label
            }
            ExposedDropDownMenu(options,selectedText,label,onValueChange)
        }
        "CA"->{
            val options = listOf("No vessel colored","1 vessel colored","2 vessel colored","3 vessel colored","4 vessel colored")
            val selectedText = when(value){
                "0"->"No vessel colored"
                "1"->"1 vessel colored"
                "2"->"2 vessel colored"
                "3"->"3 vessel colored"
                "4"->"4 vessel colored"
                else -> label
            }
            ExposedDropDownMenu(options,selectedText,label,onValueChange)
        }
        "Thal"->{
            val options = listOf("Unknown","Normal","Fixed Defect","Reversible Defect")
            val selectedText = when(value){
                "0"->"Unknown"
                "1"->"Normal"
                "2"->"Fixed Defect"
                "3"->"Reversible Defect"
                else -> label
            }
            ExposedDropDownMenu(options,selectedText,label,onValueChange)
        }
        else -> {
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = {
                    Text(label)
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = if(label !="Name")KeyboardOptions(keyboardType = KeyboardType.Number)
                else KeyboardOptions(keyboardType = KeyboardType.Unspecified)
            )
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropDownMenu(
    options: List<String>,
    selectedText: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = {Text(label)},
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false}
        ) {
            options.forEachIndexed { index,text->
                DropdownMenuItem(
                    text = {Text(text)},
                    onClick = {
                        onValueChange(index.toString())
                        expanded = false
                    }
                )
            }
        }
    }
}
