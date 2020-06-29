package com.platdmit.simplecloudmanager.domain.converters.implement

import android.util.Log
import com.platdmit.simplecloudmanager.data.api.models.ApiAction
import com.platdmit.simplecloudmanager.data.database.entity.DbAction
import com.platdmit.simplecloudmanager.domain.converters.ActionConverter
import com.platdmit.simplecloudmanager.domain.models.Action
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

class ActionConvertImp : ActionConverter {
    override fun fromApiToDb(apiAction: ApiAction): DbAction {
        return DbAction(apiAction.id.toInt().toLong(),
                apiAction.status,
                apiAction.type,
                apiAction.startedAt,
                apiAction.completedAt,
                apiAction.resourceType,
                apiAction.initiator,
                apiAction.regionSlug, apiAction.resourceId.toInt().toLong())
    }

    override fun fromDbToDomain(dbAction: DbAction): Action {
        return Action(
                dbAction.id,
                dbAction.status,
                dbAction.type,
                actionDateConvert(dbAction.startedAt),
                actionDateConvert(dbAction.completedAt),
                dbAction.resourceType,
                dbAction.initiator,
                dbAction.regionSlug,
                dbAction.resourceId
        )
    }

    override fun fromDbToDomainList(dbList: List<DbAction>): List<Action> = dbList.map { fromDbToDomain(it) }

    override fun fromApiToDbList(apiList: List<ApiAction>): List<DbAction> = apiList.map { fromApiToDb(it) }

    private fun actionDateConvert(date: String): String {
        try {
            return DateTimeFormat.forPattern("yyyy-MM-dd, HH:mm").print(DateTime(date))
        } catch (e: Exception) {
            Log.d("CONVERT", "Exception: $e")
        }
        return ""
    }
}