package com.platdmit.mod_domains.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.platdmit.mod_domains.data.room.entity.DbDomainRecord

@Dao
interface DomainRecordDao : BaseDao<DbDomainRecord> {
    @Query("SELECT * FROM dbdomainrecord WHERE parentDomain = :id")
    fun getRecordsForDomain(id: Long): List<DbDomainRecord>?

    @Query("SELECT * FROM dbdomainrecord WHERE id = :id")
    fun getRecord(id: Long): DbDomainRecord?
}