package org.wandukong.soolleongx2

import org.wandukong.soolleongx2.Data.ReceiveResponseAlcoholData
import org.wandukong.soolleongx2.Data.RecordRequestAlcoholData
import retrofit2.Call
import retrofit2.http.*

interface ReceiveService {
    @Headers("Content-Type:application/json")
    @GET("/record")
    fun receiveAlcohol(
        @Header("jwt") token : String
    ) : Call<ReceiveResponseAlcoholData>
}