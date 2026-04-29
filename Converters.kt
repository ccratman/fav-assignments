package com.example.budgetapplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.budgetapplication.data.entities.Staging
import kotlinx.coroutines.flow.Flow

@Dao
interface StagingDAO {

    @Insert
    suspend fun insertOCRStaging(staging: Staging)

    @Query("SELECT * FROM STAGING ORDER BY S_STAGING_ID")
     fun getAllStagingById(): Flow<List<Staging>>

    @Query("DELETE FROM STAGING")
    suspend fun clearStagingTable()

}
