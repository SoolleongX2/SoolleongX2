package org.wandukong.soolleongx2

import org.wandukong.soolleongx2.Data.RecordRequestAlcoholData
import org.wandukong.soolleongx2.Data.RecordResponseAlcoholData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface RecordService {

    @Headers("Content-Type:application/json")
    @POST("")
    fun recordAlcohol(
        @Body body : RecordRequestAlcoholData
    ) : Call<RecordResponseAlcoholData>
}