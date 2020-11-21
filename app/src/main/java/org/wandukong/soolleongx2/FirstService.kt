package org.wandukong.soolleongx2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface FirstService{
    @POST("/users/signin")
    fun postLogin(
        @Body body : FirstRequestData
        //@Header("jwt") token : String
    ) : Call<FirstResponseData>
}