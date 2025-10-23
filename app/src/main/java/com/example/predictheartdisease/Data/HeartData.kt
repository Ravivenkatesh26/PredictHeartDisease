package com.example.predictheartdisease.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "records_tbl")
data class HeartData(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val sex: Float,
    val  chestPainType: Float,
    val maxHeartRate:Float,
    val exerciseInducedAngina:Float,
    val sTDepression: Float,
    val slope: Float,
    val ca: Float,
    val thal: Float
)