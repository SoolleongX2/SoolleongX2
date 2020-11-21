package org.wandukong.soolleongx2

import org.wandukong.soolleongx2.Data.ReceiveResponseAlcoholData
import org.wandukong.soolleongx2.Data.RecordRequestAlcoholData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ReceiveService {
    @Headers("Content-Type:application/json")
    @POST("")
    fun receiveAlcohol(

    ) : Call<ReceiveResponseAlcoholData>
}