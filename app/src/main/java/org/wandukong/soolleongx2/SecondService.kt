package org.wandukong.soolleongx2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface SecondService{
    @POST("/goals")
    fun postBottle(
        @Header("jwt") token :String,
        @Body body : SecondRequestData
    ) : Call<SecondResponseData>
}