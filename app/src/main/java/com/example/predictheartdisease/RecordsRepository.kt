package com.example.predictheartdisease

import com.example.predictheartdisease.Data.HeartData
import com.example.predictheartdisease.dao.Dao

class RecordsRepository(private val dao: Dao) {
    suspend fun insert(record: HeartData): Long = dao.insert(record)

    suspend fun deleteById(id: Long) = dao.deleteById(id)

    suspend fun getById(id: Long) = dao.getById(id)

    suspend fun getByName(name: String) = dao.getByName(name)

    suspend fun search(query: String):List<HeartData>{
        val trimmed = query.trim()
        val asId = trimmed.toLongOrNull()
        return if(asId != null){
            dao.getById(asId)?.let { listOf(it) }?:emptyList()
        }else{
            dao.getByName(trimmed)
        }
    }
}