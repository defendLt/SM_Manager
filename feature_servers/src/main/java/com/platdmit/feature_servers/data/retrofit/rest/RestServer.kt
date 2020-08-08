package com.platdmit.feature_servers.data.retrofit.rest

import com.platdmit.feature_servers.data.retrofit.models.ApiRequestBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface RestServer {
    @GET("vps")
    fun getServers(): Call<_root_ide_package_.com.platdmit.feature_servers.data.retrofit.models.ApiRequestBody>

    @GET("vps/{id}")
    fun getServer(@Path("id") id: Long): Call<_root_ide_package_.com.platdmit.feature_servers.data.retrofit.models.ApiRequestBody>

    @GET("vps/{id}/actions")
    fun getServerActions(@Path("id") id: Long): Call<_root_ide_package_.com.platdmit.feature_servers.data.retrofit.models.ApiRequestBody>

    @GET("vps/{id}/statistics")
    fun getServerStatistics(@Path("id") id: Long): Call<_root_ide_package_.com.platdmit.feature_servers.data.retrofit.models.ApiRequestBody>

    @GET("vps/{id}/loadAverage")
    fun getServerLoadAverage(@Path("id") id: Long): Call<_root_ide_package_.com.platdmit.feature_servers.data.retrofit.models.ApiRequestBody>

    @GET("vps/{id}/backups")
    fun getServerBackups(@Path("id") id: Long): Call<_root_ide_package_.com.platdmit.feature_servers.data.retrofit.models.ApiRequestBody>

    @DELETE("vps/{id}")
    fun dellServer(@Path("id") id: Long)
}