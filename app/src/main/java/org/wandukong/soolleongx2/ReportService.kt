package org.wandukong.soolleongx2

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET


interface ReportService {
    @GET()
    fun getGoal(
            @Body body: ReceiveRequestAlcoholData
    ): Call<GoalResponseData>

    @GET()
    fun getRecordWeek(
            @Body body: ReceiveRequestAlcoholData
    ): Call<RecordWeekResponseData>
}