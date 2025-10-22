package com.example.predictheartdisease

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: EntityData): Long

    @Query("select *from records_tbl where id =:id limit 1")
    suspend fun getById(id: Long): EntityData?

    @Query("SELECT * FROM records_tbl WHERE name = :name")
    suspend fun getByName(name: String): List<EntityData>

    @Query("DELETE FROM records_tbl WHERE id = :id")
    suspend fun deleteById(id: Long)
}