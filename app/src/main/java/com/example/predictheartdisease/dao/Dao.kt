package com.example.predictheartdisease.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.predictheartdisease.Data.HeartData

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insert(record: HeartData): Long

    @Query("select *from records_tbl where id =:id limit 1")
    suspend fun getById(id: Long): HeartData?

    @Query("SELECT * FROM records_tbl WHERE name = :name")
    suspend fun getByName(name: String): List<HeartData>

    @Query("DELETE FROM records_tbl WHERE id = :id")
    suspend fun deleteById(id: Long)
}