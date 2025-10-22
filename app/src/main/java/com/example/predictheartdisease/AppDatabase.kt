package com.example.predictheartdisease

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [EntityData::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): Dao
}
