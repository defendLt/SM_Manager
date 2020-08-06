package com.platdmit.mod_servers.data.converters

import com.platdmit.mod_servers.domain.converters.SizeConverter
import com.platdmit.mod_servers.domain.models.Size
import com.platdmit.mod_servers.data.retrofit.models.ApiSize
import com.platdmit.mod_servers.data.room.entity.DbSize
import javax.inject.Inject

class SizeConvertImp
@Inject
constructor() : SizeConverter<ApiSize, Size, DbSize> {
    override fun fromApiToDb(apiSize: ApiSize): DbSize {
        return DbSize(
                apiSize.id.toLong(),
                apiSize.slug,
                apiSize.memory,
                apiSize.vcpus,
                apiSize.disk,
                apiSize.transfer,
                apiSize.priceMonthly,
                apiSize.priceHourly,
                apiSize.linked,
                apiSize.main,
                apiSize.isTest,
                apiSize.isArchive
        )
    }

    override fun fromDbToDomain(dbSize: DbSize): Size {
        return Size(
                dbSize.id,
                dbSize.id.toString(),
                dbSize.slug,
                dbSize.memory,
                dbSize.vcpus,
                dbSize.disk,
                dbSize.transfer,
                dbSize.priceMonthly,
                dbSize.priceHourly,
                dbSize.linked,
                dbSize.main,
                dbSize.isTest,
                dbSize.isArchive
        )
    }

    override fun fromDbToDomainList(dbList: List<DbSize>): List<Size> = dbList.map { fromDbToDomain(it) }

    override fun fromApiToDbList(apiList: List<ApiSize>): List<DbSize> = apiList.map { fromApiToDb(it) }
}