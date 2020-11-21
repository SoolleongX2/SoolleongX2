package org.wandukong.soolleongx2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface SecondService{
    @Headers("Content-Type:application/json")
    @POST("/users/signin")
    fun postBottle(
        @Body body : SecondRequestData
    ) : Call<SecondResponseData>
}