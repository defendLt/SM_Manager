package com.platdmit.simplecloudmanager.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.platdmit.simplecloudmanager.data.database.entity.DbLoadAverage

@Dao
interface LoadAverageDao : BaseDao<DbLoadAverage> {
    @Query("SELECT * FROM dbloadaverage WHERE server = :serverId")
    fun getLoadAverageForServer(serverId: Long): List<DbLoadAverage>?
}