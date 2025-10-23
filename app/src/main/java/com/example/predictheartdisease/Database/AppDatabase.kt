package com.example.predictheartdisease.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.predictheartdisease.Data.HeartData
import com.example.predictheartdisease.Data.UserEntity
import com.example.predictheartdisease.dao.Dao
import com.example.predictheartdisease.dao.UserDao

@Database(
    entities = [HeartData::class, UserEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): Dao
    abstract fun userDao(): UserDao
}