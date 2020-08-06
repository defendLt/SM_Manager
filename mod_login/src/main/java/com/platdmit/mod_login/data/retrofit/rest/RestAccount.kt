package com.platdmit.mod_login.data.retrofit.rest

import com.platdmit.mod_login.data.retrofit.models.ApiAuth
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface RestAccount {
    @POST("account")
    fun getAccount() : Call<ApiAuth>

    @POST("auth/login")
    fun getApiKey(@Query("login") login: String, @Query("password") password: String): Call<ApiAuth>
}