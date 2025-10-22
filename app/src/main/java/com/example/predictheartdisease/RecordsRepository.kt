package com.example.predictheartdisease

class RecordsRepository(private val dao: Dao) {
    suspend fun insert(record: EntityData): Long = dao.insert(record)

    suspend fun deleteById(id: Long) = dao.deleteById(id)

    suspend fun getById(id: Long) = dao.getById(id)

    suspend fun getByName(name: String) = dao.getByName(name)

    suspend fun search(query: String):List<EntityData>{
        val trimmed = query.trim()
        val asId = trimmed.toLongOrNull()
        return if(asId != null){
            dao.getById(asId)?.let { listOf(it) }?:emptyList()
        }else{
            dao.getByName(trimmed)
        }
    }
}