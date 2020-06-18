package com.platdmit.simplecloudmanager.domain.converters.implement

import com.platdmit.simplecloudmanager.data.api.models.ApiDomain
import com.platdmit.simplecloudmanager.data.database.entity.DbDomain
import com.platdmit.simplecloudmanager.domain.converters.DomainConverter
import com.platdmit.simplecloudmanager.domain.models.Domain

class DomainConvertImp : DomainConverter {
    override fun fromApiToDb(apiDomain: ApiDomain): DbDomain {
        return DbDomain(apiDomain.id, apiDomain.name, apiDomain.type, apiDomain.deleteDate, apiDomain.isDelegated)
    }

    override fun fromDbToDomain(dbDomain: DbDomain): Domain {
        return Domain(dbDomain.id, dbDomain.name, dbDomain.type, dbDomain.deleteDate, dbDomain.isDelegate)
    }

    override fun fromApiToDomain(apiDomain: ApiDomain): Domain {
        return Domain(apiDomain.id, apiDomain.name, apiDomain.type, apiDomain.deleteDate, apiDomain.isDelegated)
    }

    override fun fromApiToDomainList(apiList: List<ApiDomain>): List<Domain> {
        return apiList.map { fromApiToDomain(it) }.toList()
    }

    override fun fromDbToDomainList(dbList: List<DbDomain>): List<Domain> {
        return dbList.map { fromDbToDomain(it) }.toList()
    }

    override fun fromApiToDbList(apiList: List<ApiDomain>): List<DbDomain> {
        return apiList.map { fromApiToDb(it) }.toList()
    }
}