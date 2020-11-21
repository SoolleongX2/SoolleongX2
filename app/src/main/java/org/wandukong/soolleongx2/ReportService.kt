package org.wandukong.soolleongx2

import org.wandukong.soolleongx2.Data.ReceiveRequestAlcoholData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers


interface ReportService {
    @Headers("Content-Type:application/json")
    @GET("/goal")
    fun getGoal(
        @Header("jwt") token : String
    ): Call<GoalResponseData>

}